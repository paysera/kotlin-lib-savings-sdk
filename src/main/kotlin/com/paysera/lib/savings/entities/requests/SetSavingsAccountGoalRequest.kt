package com.paysera.lib.savings.entities.requests

import org.joda.money.Money
import java.util.*

data class SetSavingsAccountGoalRequest(
    var amount: Money?,
    var dateUntil: Date?
)