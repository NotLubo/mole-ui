package com.example.moledetection_ui

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextPaint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.moledetection_ui.db.SnapshotKind
import com.example.moledetection_ui.detection.BBox
import com.example.moledetection_ui.detection.StaticDetector

class ConfirmPicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_pic)

        val okBtn = findViewById<Button>(R.id.button_ok)
        okBtn.visibility =
            if(intent.extras!!["showOk"] as Boolean)
                View.VISIBLE
            else
                View.INVISIBLE
        okBtn.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        findViewById<Button>(R.id.button_cancel).setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        displayPrediction()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    lateinit var canvas: Canvas
    lateinit var paint: Paint
    lateinit var labelPaint: Paint
    lateinit var textPaint: TextPaint

    @SuppressLint("SetTextI18n")
    fun displayPrediction(){
        val snap = StaticDetector.INSTANCE!!.snapshotInstance!!
        // the boxes look nice at 1k px, dynamic scaling is todo
        val bitmap = Bitmap.createScaledBitmap(snap.pic, 1000, 1000, false)

        val dpi = resources.displayMetrics.density

        canvas = Canvas(bitmap)
        paint = Paint()
        paint.color = Color.rgb(36, 101, 255)
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = dpi * 3

        labelPaint = Paint()
        labelPaint.color = Color.rgb(36, 101, 255)
        labelPaint.style = Paint.Style.FILL
        labelPaint.isAntiAlias = true

        textPaint = TextPaint()
        textPaint.isAntiAlias = true
        textPaint.textSize = 14 * dpi
        textPaint.color = Color.WHITE

        drawBox(
            (if(snap.kind == SnapshotKind.ROLL) "Roll" else "Coin"),
            snap.scaleBox
        )
        drawBox(snap.label, snap.lesionBox)

        findViewById<ImageView>(R.id.imageView_preview).setImageBitmap(bitmap)

        findViewById<TextView>(R.id.textView_Size).text = snap.label + " size: " +
            when(snap.kind){
                SnapshotKind.ROLL -> {
                    //prediction.label + " " + formatToTwoDecimal((prediction.bbox.width/rollWidth) *45f) + "\nx" + formatToTwoDecimal((prediction.bbox.height/rollWidth)*45f) + "mm"
                    formatToTwoDecimal((snap.lesionBox.width/snap.scaleBox.width)*45f) +
                        "x" +
                            formatToTwoDecimal((snap.lesionBox.height/snap.scaleBox.width)*45f)
                }
                SnapshotKind.COIN -> {
                    formatToTwoDecimal((snap.lesionBox.width/snap.scaleBox.width)*21.5f) +
                            "x" +
                            formatToTwoDecimal((snap.lesionBox.height/snap.scaleBox.width)*21.5f)
                }
            } + "mm"
    }

    fun drawBox(label: String, bBox: BBox){
        val dpi = resources.displayMetrics.density
        val boxLeft = bBox.x * canvas.width
        val boxTop = bBox.y * canvas.height
        val boxRight = (bBox.x + bBox.width) * canvas.width
        val boxBottom = (bBox.y + bBox.height) * canvas.height
        canvas.drawRect(boxLeft, boxTop, boxRight, boxBottom, paint)

        val width = textPaint.measureText(label) + 6 * dpi
        val height = -textPaint.ascent() + textPaint.descent()
        val yOrigin = boxTop - height - 3 * dpi
        canvas.drawRect(boxLeft, yOrigin, boxLeft + width, yOrigin + height, labelPaint)

        canvas.drawText(label, boxLeft + 3 * dpi, boxTop - 6 * dpi, textPaint)
    }

    companion object{
        fun formatToTwoDecimal(float: Float) : String{
            return "%.2f".format(float)
        }
    }
}