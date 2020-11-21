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
    /*@ColumnInfo(name="parent_lesion_id")*/ var lesionId: Int,
    //todo
    /*var time: Long, // just store ticks
    var picture: String, // let's just pray a string uri will work*/
    var pic: Bitmap,
    var kind: SnapshotKind,
    /*@Embedded(prefix = "scale_")*/ var scaleBox: BBox,
    /*@Embedded(prefix = "lesion_")*/ var lesionBox: BBox
) {
    /*@PrimaryKey(autoGenerate = true)*/ var snapshotId: Int = 0
}