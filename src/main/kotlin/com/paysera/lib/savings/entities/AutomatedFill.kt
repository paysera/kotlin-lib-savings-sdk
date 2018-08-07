package com.paysera.lib.savings.entities

import org.joda.money.Money

class AutomatedFill(
    val id: String,
    val amount: Money,
    val fromAccount: String,
    val toAccount: String,
    val period: AutomatedFillPeriod,
    val createdBy: Int
) {
}

class AutomatedFillPeriod(
    val type: String,
    val monthDay: Int?,
    val weekDay: Int?
) {
    companion object {
        val TYPE_MONTHLY = "monthly"
        val TYPE_WEEKLY = "weekly"
    }
}