package com.example.fafcalculator.core_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fafcalculator.core_db.entity.ParamsDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ParamsDao {

    @Query("SELECT * FROM params WHERE key_params = :keyParams")
    fun getParamsFlow(keyParams: String): Flow<ParamsDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParams(params: ParamsDbEntity)

}