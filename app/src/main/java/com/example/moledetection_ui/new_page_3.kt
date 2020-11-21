package com.example.moledetection_ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.moledetection_ui.db.Snapshot
import com.example.moledetection_ui.db.SnapshotKind
import com.example.moledetection_ui.detection.StaticDetector

class new_page_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_page_3)

        // todo actual data
        findViewById<TextView>(R.id.textView_beforeDate).text = "20.11.2020"
        findViewById<ImageView>(R.id.imageViewBefore).setImageBitmap(BitmapFactory.decodeStream(
            applicationContext.assets.open("mock/orig-mole.png")
        ))

        // todo the singleton must go
        val snapshot = StaticDetector.INSTANCE!!.snapshotInstance!!
        when(snapshot.kind){
            SnapshotKind.ROLL -> {
                val crop = Bitmap.createBitmap(
                    snapshot.pic,
                    (snapshot.pic.width * snapshot.scaleBox.x).toInt(),
                    (snapshot.pic.height * snapshot.scaleBox.y).toInt(),
                    (snapshot.pic.width * snapshot.scaleBox.width).toInt(),
                    (snapshot.pic.height * snapshot.scaleBox.height).toInt()
                )
                findViewById<ImageView>(R.id.imageViewAfter).setImageBitmap(crop)
            }
            SnapshotKind.COIN -> {
                findViewById<ImageView>(R.id.imageViewAfter).setImageBitmap(snapshot.pic)
            }
        }

        findViewById<TextView>(R.id.textView_afterDate).text = "21.11.2020"

        findViewById<TextView>(R.id.textView_sizeChange).text = "+0.01mm"

        findViewById<Button>(R.id.button_Save).setOnClickListener {
            val saveIntent = Intent(this, MockSaveDialog::class.java)
            startActivity(saveIntent)
        }
    }
}