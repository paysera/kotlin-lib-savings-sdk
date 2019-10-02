package com.paysera.lib.savings.entities

data class AutomatedFillPeriod(
    val type: String,
    val monthDay: Int?,
    val weekDay: Int?
) {
    companion object {
        val TYPE_MONTHLY = "monthly"
        val TYPE_WEEKLY = "weekly"
    }
}