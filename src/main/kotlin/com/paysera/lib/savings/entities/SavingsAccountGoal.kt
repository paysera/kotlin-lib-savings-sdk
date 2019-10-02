package com.paysera.lib.savings.entities

import org.joda.money.Money
import java.util.*

data class SavingsAccountGoal(
    val amount: Money?,
    val dateUntil: Date?
)