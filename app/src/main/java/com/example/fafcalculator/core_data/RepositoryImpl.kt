package com.example.fafcalculator.core_data

import android.util.Log
import com.example.fafcalculator.app.model.Const
import com.example.fafcalculator.app.model.Params
import com.example.fafcalculator.app.model.Result
import com.example.fafcalculator.core_data.mappers.toParams
import com.example.fafcalculator.core_data.mappers.toParamsDbEntity
import com.example.fafcalculator.core_db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class RepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
) : Repository {
    override suspend fun listenCurrentResult(): Flow<List<Result>> {
        return appDatabase.paramsDao().getParamsFlow(Const.KEY_PARAMS).map {
            Log.e("aaa","it.toParams().massCost.toString()")
            Log.e("aaa",it.massCost.toString())
            Log.e("aaa",it.massIncome.toString())
            getResult(it.toParams())
        }
    }

    override suspend fun setCurrentParams(params: Params) {
        appDatabase.paramsDao().insertParams(params.toParamsDbEntity())
    }

    private fun getResult(params: Params): List<Result> {

        val resultList: MutableList<Result> = ArrayList()
        val massCost = params.massCost
        var massIncome = params.massIncome
        var massCurrent = 0
        var sec = 0.0f
        val secMax = 600.0f
        var sacu = 0
        val sacuCost = 6450  //5320
        val sacuIncome = 11
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