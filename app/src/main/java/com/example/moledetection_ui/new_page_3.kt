package com.example.moledetection_ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
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

    lateinit var oldSnap: Snapshot;

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_page_3)

        oldSnap = StaticDb.currentLesion!!.snapshot!!

        findViewById<TextView>(R.id.textView_beforeDate).text = oldSnap.time.toString()
        findViewById<ImageView>(R.id.imageViewBefore).setImageBitmap(cropImageIfRoll(oldSnap))

        // todo the singleton must go
        val snapshot = StaticDetector.INSTANCE!!.snapshotInstance!!
        findViewById<ImageView>(R.id.imageViewAfter).setImageBitmap(cropImageIfRoll(snapshot))

        findViewById<TextView>(R.id.textView_afterDate).text = snapshot.time.toString()

        val origSize = oldSnap.getRealSize()
        val newSize = snapshot.getRealSize()

        findViewById<TextView>(R.id.textView_beforeSize).text = "Original size: " + Exporter.getSizeString(origSize) + "mm"
        findViewById<TextView>(R.id.textView_afterSize).text = "New size: " + Exporter.getSizeString(newSize) + "mm"

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
            StaticDb.currentLesion!!.snapshot = snapshot
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