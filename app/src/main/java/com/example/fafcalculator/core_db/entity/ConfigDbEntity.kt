package com.example.fafcalculator.core_db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fafcalculator.app.model.Result
import com.example.fafcalculator.app.model.SacuCost

@Entity(tableName = "config")
data class ConfigDbEntity(
    @PrimaryKey @ColumnInfo(name = "key_config") val keyConfig: String,
    @ColumnInfo(name = "mass_cost") val massCost: Int,
    @ColumnInfo(name = "mass_income") val massIncome: Int,
    @ColumnInfo(name = "sacu_income") val sacuIncome: Int,
    @ColumnInfo(name = "sacu_cost") val sacuCost: SacuCost,
    @ColumnInfo(name = "sec_max") val secMax: Int,
)