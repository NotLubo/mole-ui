package com.example.moledetection_ui.db

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moledetection_ui.R
import com.example.moledetection_ui.detection.BBox
import java.time.LocalDateTime

// please do not tell my future employers about this
class StaticDb {
    companion object{

        var lesions = ArrayList<Lesion>()

        var currentLesion: Lesion? = null

        @RequiresApi(Build.VERSION_CODES.O)
        public fun initialize(context: Context){
            /*snapshot = Snapshot(
                    BitmapFactory.decodeResource(context.resources, R.drawable.before_orig),
                    SnapshotKind.ROLL,
                    label = "Defect",
                    LocalDateTime.of(2020, 11, 21, 22, 13, 12),
                    BBox(0.26057765f, 0.556597f, 0.10543507f, 0.5095397f),
                    BBox(0.44064185f, 0.14090028f, 0.27025524f, 0.1478554f)
            )*/
        }

    }

}