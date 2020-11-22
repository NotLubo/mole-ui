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
import com.example.moledetection_ui.db.StaticDb

class new_page_1 : AppCompatActivity() {
    lateinit var mBodyView: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_page_1)

        StaticDb.initialize(this.applicationContext)

        mBodyView = findViewById<ImageView>(R.id.imageView);

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

}