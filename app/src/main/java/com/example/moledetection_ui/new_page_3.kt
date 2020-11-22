package com.example.moledetection_ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.moledetection_ui.db.Snapshot
import com.example.moledetection_ui.db.SnapshotKind
import com.example.moledetection_ui.db.StaticDb
import com.example.moledetection_ui.detection.StaticDetector

class new_page_3 : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_page_3)

        findViewById<TextView>(R.id.textView_beforeDate).text = StaticDb.snapshot.time.toString()
        findViewById<ImageView>(R.id.imageViewBefore).setImageBitmap(StaticDb.snapshot.pic)


        // todo the singleton must go
        val snapshot = StaticDetector.INSTANCE!!.snapshotInstance!!
        findViewById<ImageView>(R.id.imageViewAfter).setImageBitmap(cropImageIfRoll(snapshot))

        findViewById<TextView>(R.id.textView_afterDate).text = snapshot.time.toString()

        val origSize = StaticDb.snapshot.getRealSize()
        val newSize = snapshot.getRealSize()
        val increase =
                (((newSize[0]*newSize[1])/(origSize[0]*origSize[1]))*100f)-100f

        findViewById<TextView>(R.id.textView_sizeChange).text = "%.3f".format(increase) + "%"

        findViewById<Button>(R.id.button_Share).setOnClickListener {
            Exporter.export(this.applicationContext).also {
                val pdfUri: Uri = FileProvider.getUriForFile(
                    this.applicationContext,
                    "com.example.moledetection_ui",
                    it
                )
                var openIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, pdfUri)
                    type = "application/pdf"
                }
                startActivity(openIntent)
            }
        }

        findViewById<Button>(R.id.button_Save).setOnClickListener{
            StaticDb.snapshot = snapshot
            setResult(Activity.RESULT_OK)
            finish()
        }

    }

    companion object{
        fun cropImageIfRoll(snapshot: Snapshot) : Bitmap{
            return when(snapshot.kind){
                SnapshotKind.ROLL -> {
                    Bitmap.createBitmap(
                            snapshot.pic,
                            (snapshot.pic.width * snapshot.scaleBox.x).toInt(),
                            (snapshot.pic.height * snapshot.scaleBox.y).toInt(),
                            (snapshot.pic.width * snapshot.scaleBox.width).toInt(),
                            (snapshot.pic.height * snapshot.scaleBox.height).toInt()
                    )
                }
                SnapshotKind.COIN -> {
                    snapshot.pic
                }
            }
        }
    }
}