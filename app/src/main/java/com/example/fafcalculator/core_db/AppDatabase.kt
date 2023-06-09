package com.example.fafcalculator.core_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fafcalculator.core_db.dao.ConfigDao
import com.example.fafcalculator.core_db.entity.ConfigDbEntity

@Database(
    entities = [
        ConfigDbEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun configDao(): ConfigDao
}