package com.paysera.lib.savings.entities.requests

import org.joda.money.Money

class CreateAutomatedFillRequest(
    var amount: Money? = null,
    var fromAccount: String? = null,
    var toAccount: String? = null,
    var periodType: String? = null,
    var weekDay: Int? = null,
    var monthDay: Int? = null
) {
    companion object {
        val PERIOD_TYPE_MONTHLY = "monthly"
        val PERIOD_TYPE_WEEKLY = "weekly"
    }
}