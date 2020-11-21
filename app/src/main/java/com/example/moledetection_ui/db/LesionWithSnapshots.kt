package com.example.moledetection_ui.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

/*@Entity
data class LesionWithSnapshots (
    @Embedded var lesion: Lesion,
    @Relation(
        parentColumn = "lesion_id",
        entityColumn = "parent_lesion_id"
    )
    var snapshots: List<Snapshot>
)*/