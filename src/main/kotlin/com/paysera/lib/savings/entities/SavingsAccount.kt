package com.paysera.lib.savings.entities

import org.joda.money.Money
import java.util.*

class SavingsAccount(
    val id: String,
    val type: String,
    val accountNumber: String,
    val goal: SavingsAccountGoal?,
    val displayUrl: String?
) {
    companion object {
        val TYPE_PRIVATE = "private"
        val TYPE_PUBLIC = "public"
    }
}

class SavingsAccountGoal(
    val amount: Money?,
    val dateUntil: Date?
)