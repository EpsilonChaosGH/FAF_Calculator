package com.example.fafcalculator.core_db.dao

import androidx.room.*
import com.example.fafcalculator.core_db.entity.ConfigDbEntity
import com.example.fafcalculator.core_db.entity.UpdateCostTuple
import com.example.fafcalculator.core_db.entity.UpdateParamsTuple
import com.example.fafcalculator.core_db.entity.UpdateSettingsTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigDao {

    @Query("SELECT * FROM config WHERE key_config = :keyParams")
    fun getConfigFlow(keyParams: String): Flow<ConfigDbEntity>

    @Query("SELECT * FROM config WHERE key_config = :keyParams")
    fun getConfig(keyParams: String): ConfigDbEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfig(config: ConfigDbEntity)

    @Update(entity = ConfigDbEntity::class)
    suspend fun updateParams(params: UpdateParamsTuple)

    @Update(entity = ConfigDbEntity::class)
    suspend fun updateCost(params: UpdateCostTuple)

    @Update(entity = ConfigDbEntity::class)
    suspend fun updateSettings(settings: UpdateSettingsTuple)

}