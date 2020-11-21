package com.example.moledetection_ui.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesions")
data class Lesion(val x: Float, val y: Float){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="lesion_id")
    var lesionId: Int = 0
    // todo tbd
    var name: String = ""
}

