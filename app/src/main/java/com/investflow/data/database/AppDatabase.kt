package com.investflow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.investflow.data.database.converters.DateConverter
import com.investflow.data.database.dao.CashFlowEntryDao
import com.investflow.data.database.dao.ProjectDao
import com.investflow.data.database.entities.CashFlowEntry
import com.investflow.data.database.entities.Project

/**
 * Banco de dados principal do aplicativo
 */
@Database(
    entities = [Project::class, CashFlowEntry::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun cashFlowEntryDao(): CashFlowEntryDao
}