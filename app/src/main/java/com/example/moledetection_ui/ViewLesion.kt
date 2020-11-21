package com.example.moledetection_ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ViewLesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_lesion)
        // todo many things
        findViewById<ImageView>(R.id.mockLesionView).setOnClickListener{
            //val lesion = mLesions.get(0)
            val intent = Intent(this, new_page_2::class.java).apply {
                this.putExtra("lesionId", this@ViewLesion.intent.extras!!["lesionId"] as Int)
            }
            startActivity(intent)
        }
    }
}