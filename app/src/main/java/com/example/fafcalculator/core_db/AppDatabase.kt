package com.example.fafcalculator.core_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fafcalculator.core_db.dao.ParamsDao
import com.example.fafcalculator.core_db.entity.ParamsDbEntity

@Database(
    entities = [
        ParamsDbEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun paramsDao(): ParamsDao
}