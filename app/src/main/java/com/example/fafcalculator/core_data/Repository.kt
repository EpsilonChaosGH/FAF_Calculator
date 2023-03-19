package com.example.fafcalculator.core_data

import com.example.fafcalculator.app.model.Params
import com.example.fafcalculator.app.model.Result
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun listenCurrentResult(): Flow<List<Result>>

    suspend fun setCurrentParams(params: Params)
}