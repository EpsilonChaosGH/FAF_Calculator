package com.example.fafcalculator.core_data

import com.example.fafcalculator.app.model.*
import com.example.fafcalculator.core_data.mappers.toConfig
import com.example.fafcalculator.core_db.AppDatabase
import com.example.fafcalculator.core_db.entity.UpdateCostTuple
import com.example.fafcalculator.core_db.entity.UpdateParamsTuple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class RepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
) : Repository {
    override suspend fun listenCurrentResult(): Flow<MainState> {
        return appDatabase.configDao().getConfigFlow(Const.KEY_CONFIG).map {
            val config = it.toConfig()
            MainState(resultList = getResult(config), config = config)
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
        var sec = 0.0f
        val secMax = config.secMax
        var sacu = 0
        val sacuCost = config.sacuCost //6450-5320
        val sacuIncome = config.sacuIncome
        var bestResult = 0

        fun toMinutes(): Float {
            return ((((sec + (massCost / massIncome)) / 60.0f)) * 100.0f).roundToInt() / 100.0f
        }

        var best = toMinutes()

        fun resultAll(sacu: Int, massIncome: Int) {
            resultList.add(sacu, Result(sacu, massIncome, toMinutes(), false))
            if (toMinutes() < best) {
                best = toMinutes()
                bestResult = sacu
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

        resultList[bestResult].best = true
        return resultList

    }
}