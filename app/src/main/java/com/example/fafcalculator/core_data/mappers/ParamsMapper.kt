package com.example.fafcalculator.core_data.mappers

import com.example.fafcalculator.app.model.Const
import com.example.fafcalculator.app.model.Params
import com.example.fafcalculator.core_db.entity.ParamsDbEntity

fun Params.toParamsDbEntity(): ParamsDbEntity = ParamsDbEntity(
    keyParams = Const.KEY_PARAMS,
    massCost = massCost,
    massIncome = massIncome
)

fun ParamsDbEntity.toParams(): Params = Params(
    massCost = massCost,
    massIncome = massIncome
)