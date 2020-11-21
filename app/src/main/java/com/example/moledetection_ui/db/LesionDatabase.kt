package com.example.moledetection_ui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Lesion::class/*, Snapshot::class*/], version = 1, exportSchema = false)
@TypeConverters(SnapshotKindConverter::class)
abstract class LesionDatabase : RoomDatabase() {
    abstract fun lesionDao(): LesionDao
   // abstract fun snapshotDao(): SnapshotDao

    companion object{
        @Volatile
        private var INSTANCE: LesionDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : LesionDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LesionDatabase::class.java,
                    "lesion_database"
                )
                    .fallbackToDestructiveMigration() // TODO not fit for production
                    .addCallback(PopulateDatabaseCallback(scope)) // TODO for testing purposes, remove on final
                    .build()
                INSTANCE = instance
                instance
            }

        }
        private class PopulateDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        // if the db is empty, add a dummy lesion
                        val dao = database.lesionDao();
                        if(!dao.hasLesions()){
                            dao.insert(Lesion(50f, 50f))
                        }
                    }
                }
            }
        }
    }
}