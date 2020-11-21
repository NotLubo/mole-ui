package com.example.moledetection_ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import com.example.moledetection_ui.db.Lesion
import com.example.moledetection_ui.db.LesionDatabase
import kotlinx.coroutines.flow.collect

class new_page_1 : AppCompatActivity() {
    lateinit var mBodyView: ImageView
    lateinit var mLesions: List<Lesion>

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_page_1)

        mBodyView = findViewById<ImageView>(R.id.imageView);
        // forgive me padre for I have sinned
        // this is absolute heresy from an app arch standpoint
        // TODO disregard DB, acquire demo
        mLesions = listOf<Lesion>(Lesion(2f, 2f))
        // todo disregard drawing, apply mspaint, acquire demo
        //drawLesions()
        /*(application as TrackerApplication).database.lesionDao().getLesions().observe(this){
            mLesions = it
            drawLesions()
        }*/

        mBodyView.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN -> {
                    // TODO actually picking a lesion
                    val lesion = mLesions.get(0)
                    val intent = Intent(this, ViewLesion::class.java).apply {
                        this.putExtra("lesionId", lesion.lesionId)
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