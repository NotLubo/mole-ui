package com.example.moledetection_ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.text.TextPaint
import androidx.annotation.RequiresApi
import com.example.moledetection_ui.db.StaticDb
import com.example.moledetection_ui.detection.StaticDetector
import java.io.File
import java.io.IOException
import java.util.*

class Exporter {
    companion object {
        const val A4_WIDTH = 842
        const val A4_HEIGHT = 595
        const val MARGIN = 36
        const val DRAWABLE_WIDTH = A4_WIDTH - MARGIN
        const val DRAWABLE_HEIGHT = A4_HEIGHT - MARGIN

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun export(context: Context): File{
            val doc = PdfDocument()
            // in postscript units, this is landscape A4 w/ 1/2" margins
            val pageInfo =
                PdfDocument.PageInfo.Builder(A4_WIDTH, A4_HEIGHT, 1)
                    .setContentRect(Rect(MARGIN, MARGIN, DRAWABLE_WIDTH, DRAWABLE_HEIGHT))
                    .create()

            val page = doc.startPage(pageInfo)
            val canvas = page.canvas

            /*val mockSource = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.orig_mole
            )

            val scaled = Bitmap.createScaledBitmap(mockSource, 1000, 1000, false)*/

            var snapshot = StaticDetector.INSTANCE!!.snapshotInstance!!

            val halfPage = (DRAWABLE_WIDTH-MARGIN)/2

            canvas.drawBitmap(StaticDb.snapshot.pic, null, Rect(0, 0, halfPage, halfPage), null)
            canvas.drawBitmap(snapshot.pic,null, Rect(halfPage, 0, DRAWABLE_WIDTH - MARGIN, halfPage), null)

            val origSize = StaticDb.snapshot.getRealSize()
            val newSize = snapshot.getRealSize()
            val increase =
                    (((newSize[0]*newSize[1])/(origSize[0]*origSize[1]))*100f)-100f

            val text =
                    StaticDb.snapshot.time.toString() + " " +
                            getSizeString(origSize) + "mm; "+
                            snapshot.time.toString() + " " +
                            getSizeString(newSize) + "mm; "+
                            "Growth " + "%.3f".format(increase) + "%"


            canvas.drawText(
                text,
                0f,
                halfPage+18f,
                TextPaint().apply {
                    textSize = 10f
                    color = Color.BLACK
                }
            )

            doc.finishPage(page)

            var tempFile = createPdfFile(context)
            doc.writeTo(tempFile.outputStream())
            doc.close()

            return tempFile
        }

        // todo DRY this is pretty much copypasted from photo capture
        @Throws(IOException::class)
        private fun createPdfFile(context: Context): File {
            // Create an image file name
            val timeStamp: String = Date().time.toString()
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(
                "Export_${timeStamp}_", /* prefix */
                ".pdf", /* suffix */
                storageDir /* directory */
            ).apply {
                deleteOnExit()
            }
        }

        private fun getSizeString(size: Array<Float>): String{
            return "%.3f".format(size[0]) + "x" + "%.3f".format(size[1])
        }
    }
}