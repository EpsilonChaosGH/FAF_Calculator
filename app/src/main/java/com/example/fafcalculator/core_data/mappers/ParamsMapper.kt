package com.example.fafcalculator.core_data.mappers

import com.example.fafcalculator.app.model.Config
import com.example.fafcalculator.core_db.entity.ConfigDbEntity

fun ConfigDbEntity.toConfig(): Config = Config(
    massCost = massCost,
    massIncome = massIncome,
    sacuIncome = sacuIncome,
    sacuCost = sacuCost,
    secMax = secMax
)