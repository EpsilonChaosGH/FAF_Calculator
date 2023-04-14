package com.example.fafcalculator.core_data

import com.example.fafcalculator.app.model.*
import com.example.fafcalculator.core_data.mappers.toConfig
import com.example.fafcalculator.core_db.AppDatabase
import com.example.fafcalculator.core_db.entity.UpdateCostTuple
import com.example.fafcalculator.core_db.entity.UpdateParamsTuple
import com.example.fafcalculator.core_db.entity.UpdateSettingsTuple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class RepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
) : Repository {
    override suspend fun getResultFlow(): Flow<List<Result>> {
        return appDatabase.configDao().getConfigFlow(Const.KEY_CONFIG).map {
            getResult(it.toConfig())
        }
    }

    override suspend fun getConfigFlow(): Flow<Config> {
        return appDatabase.configDao().getConfigFlow(Const.KEY_CONFIG).map {
            it.toConfig()
        }
    }

    override suspend fun setCurrentParams(params: Params) {
        appDatabase.configDao().updateParams(
            UpdateParamsTuple(
                keyConfig = Const.KEY_CONFIG,
                massCost = params.massCost,
                massIncome = params.massIncome
            )
        )
    }

    override suspend fun setCurrentSettings(settings: Settings) {
        appDatabase.configDao().updateSettings(
            UpdateSettingsTuple(
                keyConfig = Const.KEY_CONFIG,
                sacuIncome = settings.sacuIncome,
                sacuCost = settings.sacuCost,
                secMax = settings.secMax
            )
        )
    }

    override suspend fun setCost(cost: Int) {
        appDatabase.configDao().updateCost(
            UpdateCostTuple(
                keyConfig = Const.KEY_CONFIG,
                massCost = cost
            )
        )
    }

    private fun getResult(config: Config): List<Result> {

        val resultList: MutableList<Result> = ArrayList()
        val massCost = config.massCost
        var massIncome = config.massIncome
        var massCurrent = 0
        var sec = 0
        val secMax = config.secMax //1200
        var sacu = 0
        val sacuCost = config.sacuCost //6450-5320
        val sacuIncome = config.sacuIncome
        var bestResultIndex = 0

        fun currentTime(): Int {
            return sec + (massCost / massIncome)
        }

        var bestResult = currentTime()

        fun resultAll(sacu: Int, massIncome: Int) {
            resultList.add(sacu, Result(sacu, massIncome, currentTime(), false))
            if (currentTime() < bestResult) {
                bestResult = currentTime()
                bestResultIndex = sacu
            }
        }

        resultAll(0, massIncome)

        while (sec < secMax) {
            massCurrent += massIncome
            sec++
            if (massCurrent >= sacuCost) {
                sacu++
                massIncome += sacuIncome
                massCurrent -= sacuCost
                resultAll(sacu, massIncome)
            }
        }

        resultList[bestResultIndex].best = true
        return resultList
    }
}