package com.example.moledetection_ui.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LesionDao{
    @Query("SELECT * FROM lesions")
    fun getLesions(): LiveData<List<Lesion>>

    @Query("SELECT EXISTS(SELECT * FROM lesions)")
    fun hasLesions(): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT) // todo is strategy needed?
    suspend fun insert(lesion: Lesion)

    // todo: need to resolve child snapshots
    /*@Query("DELETE FROM lesions WHERE lesion_id = :lesionId")
    suspend fun remove(lesionId: Int)*/
}