package com.example.fafcalculator.core_data.mappers

import com.example.fafcalculator.app.model.Const
import com.example.fafcalculator.app.model.Config
import com.example.fafcalculator.core_db.entity.ConfigDbEntity

//fun Config.toParamsDbEntity(): ConfigDbEntity = ConfigDbEntity(
//    keyConfig = Const.KEY_PARAMS,
//    massCost = massCost,
//    massIncome = massIncome
//)

fun ConfigDbEntity.toConfig(): Config = Config(
    massCost = massCost,
    massIncome = massIncome,
    sacuIncome = sacuIncome,
    sacuCost = sacuCost,
    secMax = secMax
)