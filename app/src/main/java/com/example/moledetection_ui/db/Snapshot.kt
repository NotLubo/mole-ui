package com.example.moledetection_ui.db

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moledetection_ui.detection.BBox
import java.time.LocalDateTime

//@Entity(tableName = "snapshot")
data class Snapshot(
    //todo
    /*var time: Long, // just store ticks
    var picture: String, // let's just pray a string uri will work*/
    var pic: Bitmap,
    var kind: SnapshotKind,
    var label: String,
    var time: LocalDateTime,
    /*@Embedded(prefix = "scale_")*/ var scaleBox: BBox,
    /*@Embedded(prefix = "lesion_")*/ var lesionBox: BBox
) {
    /*@PrimaryKey(autoGenerate = true)*/ var snapshotId: Int = 0
    public fun getRealSize() : Array<Float>{
        var scaleFactor = when(kind){
            SnapshotKind.ROLL -> 45f
            SnapshotKind.COIN -> 21.5f
        }
        return arrayOf(
                (lesionBox.width/scaleBox.width)*scaleFactor,
                (lesionBox.height/scaleBox.width)*scaleFactor
        )
    }
}