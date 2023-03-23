package com.example.fafcalculator.core_data

import com.example.fafcalculator.app.model.MainState
import com.example.fafcalculator.app.model.Params
import com.example.fafcalculator.app.model.Settings
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun listenCurrentResult(): Flow<MainState>

    suspend fun setCurrentParams(params: Params)

    suspend fun setCurrentSettings(settings: Settings)

    suspend fun setCost(cost: Int)
}