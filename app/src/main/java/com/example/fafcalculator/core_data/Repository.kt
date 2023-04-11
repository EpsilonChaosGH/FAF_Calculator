package com.example.fafcalculator.core_data

import com.example.fafcalculator.app.model.Config
import com.example.fafcalculator.app.model.Params
import com.example.fafcalculator.app.model.Result
import com.example.fafcalculator.app.model.Settings
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getResultFlow(): Flow<List<Result>>

    suspend fun getConfigFlow(): Flow<Config>

    suspend fun setCurrentParams(params: Params)

    suspend fun setCurrentSettings(settings: Settings)

    suspend fun setCost(cost: Int)
}