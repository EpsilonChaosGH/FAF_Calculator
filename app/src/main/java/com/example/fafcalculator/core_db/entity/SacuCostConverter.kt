package com.example.fafcalculator.core_db.entity

import androidx.room.TypeConverter
import com.example.fafcalculator.app.model.SacuCost

class SacuCostConverter {

    @TypeConverter
    fun toSacuCost(value: String) = enumValueOf<SacuCost>(value)

    @TypeConverter
    fun fromSacuCost(value: SacuCost) = value.name
}