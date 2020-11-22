package com.example.moledetection_ui

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.moledetection_ui.db.StaticDb

class ViewLesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_lesion)
        // todo many things
        findViewById<Button>(R.id.button_update).setOnClickListener{
            val intent = Intent(this, new_page_2::class.java)
            startActivityForResult(intent, 0)
        }
        refresh()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        refresh()
    }

    fun refresh(){
        findViewById<TextView>(R.id.textView_lesionViewTitle).text = StaticDb.currentLesion!!.name
        findViewById<TextView>(R.id.textView_lesionViewLastChecked).text =
                StaticDb.currentLesion!!.snapshot!!.time.toString()
        findViewById<ImageView>(R.id.imageView_viewLesion).setImageBitmap(
                // constant size makes crazy Android SDKs happy
                Bitmap.createScaledBitmap(StaticDb.currentLesion!!.snapshot!!.pic, 1000, 1000, false)
        )
    }
}