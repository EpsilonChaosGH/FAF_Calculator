package com.example.fafcalculator.core_db.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.fafcalculator.app.model.SacuCost


data class UpdateParamsTuple(
    @ColumnInfo(name = "key_config") @PrimaryKey val keyConfig: String,
    @ColumnInfo(name = "mass_cost") val massCost: Int,
    @ColumnInfo(name = "mass_income") val massIncome: Int
)

data class UpdateCostTuple(
    @ColumnInfo(name = "key_config") @PrimaryKey val keyConfig: String,
    @ColumnInfo(name = "mass_cost") val massCost: Int,
)

data class UpdateSettingsTuple(
    @ColumnInfo(name = "key_config") @PrimaryKey val keyConfig: String,
    @ColumnInfo(name = "sacu_income") val sacuIncome: Int,
    @ColumnInfo(name = "sacu_cost") val sacuCost: SacuCost,
    @ColumnInfo(name = "sec_max") val secMax: Int,
)