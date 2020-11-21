package com.example.moledetection_ui.db

import androidx.room.TypeConverter

enum class SnapshotKind {
    ROLL, COIN
}

class SnapshotKindConverter {
    @TypeConverter
    fun fromSnapshotKind(value: SnapshotKind) : Int {
        return value.ordinal
    }
    @TypeConverter
    fun toSnapshotKind(value: Int) : SnapshotKind {
        return enumValues<SnapshotKind>()[value]
    }
}