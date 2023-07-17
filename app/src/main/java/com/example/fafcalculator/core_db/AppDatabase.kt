package com.example.fafcalculator.core_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fafcalculator.core_db.dao.ConfigDao
import com.example.fafcalculator.core_db.entity.ConfigDbEntity
import com.example.fafcalculator.core_db.entity.SacuCostConverter

@Database(
    entities = [
        ConfigDbEntity::class
    ], version = 1
)
@TypeConverters(SacuCostConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun configDao(): ConfigDao
}