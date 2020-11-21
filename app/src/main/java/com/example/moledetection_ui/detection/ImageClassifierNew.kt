package com.example.moledetection_ui.detection

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min

internal class ImageClassifierNew(
        context: Context,
        modelPath: String,
        private val mDetections: Array<String>,
        private val mScoreThreshold: Array<Float>) {

    private val mInterpreter: Interpreter

    init {
        val options = Interpreter.Options()
        // todo gpu processing in the future?
        options.setNumThreads(NUM_THREADS)
        mInterpreter = Interpreter(loadModelFile(context, modelPath), options)
    }

    @Throws(IOException::class)
    private fun loadModelFile(context: Context, modelPath: String): MappedByteBuffer {
        // todo taken directly from tflite sample but I guess we should close the file at some point
        val fileDescriptor = context.assets.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun detect(bitmap: Bitmap) : List<Prediction>
    {
        // todo we could preallocate
        val imgData = ByteBuffer.allocateDirect(INPUT_HEIGHT * INPUT_WIDTH * 3)
        imgData.order(ByteOrder.nativeOrder())
        imgData.rewind()
        val intValues = IntArray(INPUT_HEIGHT * INPUT_WIDTH)

        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        for (pixelValue in intValues){
            imgData.put((pixelValue shr 16 and 0xFF).toByte())
            imgData.put((pixelValue shr 8 and 0xFF).toByte())
            imgData.put((pixelValue and 0xFF).toByte())
        }

        val outputLocations = Array(1) { Array(NUM_DETECTIONS) { FloatArray(4) } }
        val outputClasses = Array(1) { FloatArray(NUM_DETECTIONS) }
        val outputScores = Array(1) { FloatArray(NUM_DETECTIONS) }
        val numDetections = FloatArray(1)

        val inputArray = arrayOf<Any>(imgData)
        val outputMap: MutableMap<Int, Any> = HashMap()
        outputMap[0] = outputLocations
        outputMap[1] = outputClasses
        outputMap[2] = outputScores
        outputMap[3] = numDetections

        mInterpreter.runForMultipleInputsOutputs(inputArray, outputMap)

        val numDetectionsOutput = min(NUM_DETECTIONS, numDetections[0].toInt())
        val result = ArrayList<Prediction>(numDetectionsOutput)

        for (i in 0 until numDetectionsOutput){
            val outputClass = outputClasses[0][i].toInt()
            if(outputScores[0][i] > mScoreThreshold[outputClass]){
                result.add(
                        Prediction(
                                mDetections[outputClass],
                                outputScores[0][i],
                                /**
                                 * 0 - top
                                 * 1 - left
                                 * 2 - bottom
                                 * 3 - right
                                 */
                                BBox.fromCoords(
                                        outputLocations[0][i][1],
                                        outputLocations[0][i][0],
                                        outputLocations[0][i][3],
                                        outputLocations[0][i][2]
                                )
                        )
                )
            }
        }

        return result
    }

    companion object{
        // todo should be more universal
        public const val INPUT_HEIGHT = 512;
        public const val INPUT_WIDTH = 512;
        // taken from metadata.json maxDetections
        public const val NUM_DETECTIONS = 40;
        // todo detect this
        public const val NUM_THREADS = 4;
    }
}