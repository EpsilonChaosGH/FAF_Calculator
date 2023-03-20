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
        return appDatabase.configDao().getParamsFlow(Const.KEY_CONFIG).map {
            getResult(it.toConfig())
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

    private fun getResult(config: Config): MainState {

        val resultList: MutableList<Result> = ArrayList()
        var massCurrent = 0
        var sec = 0.0f
        var sacu = 0
        var bestResult = 0
//        val secMax = 600.0f
//        val sacuCost = 6450  //5320
//        val sacuIncome = 11

        fun toMinutes(): Float {
            return ((((sec + (config.massCost / config.massIncome)) / 60.0f)) * 100.0f).roundToInt() / 100.0f
        }

        var best = toMinutes()

        fun resultAll(sacu: Int, massIncome: Int) {
            resultList.add(sacu, Result(sacu, massIncome, toMinutes(), false))
            if (toMinutes() < best) {
                best = toMinutes()
                bestResult = sacu
            }
        }

        resultAll(0, config.massIncome)

        while (sec < config.secMax) {
            massCurrent += config.massIncome
            sec++
            if (massCurrent >= config.sacuCost) {
                sacu++
                config.massIncome += config.sacuIncome
                massCurrent -= config.sacuCost
                resultAll(sacu, config.massIncome)
            }
        }

        resultList[bestResult].best = true
        return MainState(
            resultList = resultList,
            config = config
        )
    }
}