package com.example.fafcalculator.app.model


data class Config(
    val massCost: Int,
    val massIncome: Int,
    val sacuIncome: Int,
    val sacuCost: SacuCost,
    val secMax: Int,
)

