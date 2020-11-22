package com.example.moledetection_ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.example.moledetection_ui.db.Lesion
import com.example.moledetection_ui.db.StaticDb

class new_page_1 : AppCompatActivity() {
    lateinit var mBodyView: ImageView
    lateinit var mLesions: List<Lesion>

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_page_1)

        StaticDb.initialize(this.applicationContext)

        mBodyView = findViewById<ImageView>(R.id.imageView);
        // todo disregard drawing, apply mspaint, acquire demo
        //drawLesions()

        mBodyView.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    // TODO actually picking a lesion
                    val intent = Intent(this, ViewLesion::class.java).apply {
                        this.putExtra("lesionId", 0)
                    }
                    startActivity(intent)


                    true
                }
                else -> false
            }
        }
    }

    fun drawLesions(){
        val bitmap = BitmapFactory.decodeResource(
            this.applicationContext.resources,
            R.drawable.istockphoto_881016858_612x612
        ).copy(
            Bitmap.Config.ARGB_8888, true)

        val canvas = Canvas(bitmap)

        val dpi = resources.displayMetrics.density
        val paint = Paint()
        // todo common colors
        paint.color = Color.rgb(36, 101, 255)
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        //paint.strokeWidth = dpi * 3

        for(lesion in mLesions!!){
            /*canvas.drawCircle(
                lesion.x,
                lesion.y,
                20f*dpi,
                paint
            )*/
            canvas.drawRect(
                lesion.x-50f,
                lesion.y-50f,
                lesion.x+50f,
                lesion.y+50f,
                paint
            )
        }

        mBodyView.setImageBitmap(bitmap)
    }


}