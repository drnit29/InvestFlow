package com.investflow

import android.app.Application
import androidx.room.Room
import com.investflow.data.database.AppDatabase

class InvestFlowApplication : Application() {
    
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        
        // Initialize Room database
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "investflow_database"
        ).build()
    }
}