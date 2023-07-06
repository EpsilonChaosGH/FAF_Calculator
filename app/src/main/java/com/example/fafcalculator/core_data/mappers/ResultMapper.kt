package com.example.fafcalculator.core_data.mappers

import com.example.fafcalculator.app.model.Result
import com.example.fafcalculator.app.model.ResultState
import java.text.SimpleDateFormat
import java.util.Locale

fun Result.toResultState() = ResultState(
    sacu = sacu.toString(),
    massIncome = massIncome.toString(),
    time = secToDataFormat(time.toLong()),
    best = best
)

private fun secToDataFormat(sec: Long): String {
    return when (sec) {
        in 0..3600 -> SimpleDateFormat("mm:ss", Locale.getDefault()).format(sec * 1000)
        in 3600..Long.MAX_VALUE -> SimpleDateFormat(
            "hh:mm:ss:",
            Locale.getDefault()
        ).format(sec * 1000)
        else -> "..."
    }
}