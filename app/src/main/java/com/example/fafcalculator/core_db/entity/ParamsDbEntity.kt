package com.example.fafcalculator.core_db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "params")
data class ParamsDbEntity(
@PrimaryKey @ColumnInfo(name = "key_params") val keyParams: String,
@ColumnInfo(name = "mass_cost") val massCost: Int,
@ColumnInfo(name = "mass_income") val massIncome: Int,
)