package com.paysera.lib.savings.entities

import org.joda.money.Money

data class AutomatedFill(
    val id: String,
    val amount: Money,
    val fromAccount: String,
    val toAccount: String,
    val period: AutomatedFillPeriod,
    val createdBy: Int
)