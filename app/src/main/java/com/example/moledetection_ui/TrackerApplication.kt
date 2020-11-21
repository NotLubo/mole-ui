package com.example.moledetection_ui

import android.app.Application
import com.example.moledetection_ui.db.LesionDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TrackerApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { LesionDatabase.getDatabase(this, applicationScope) }
}