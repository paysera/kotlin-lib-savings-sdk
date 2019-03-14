package com.paysera.lib.savings.entities.requests

import com.paysera.lib.savings.entities.Period
import org.joda.money.Money

class CreateAutomatedFillRequest(
    var amount: Money? = null,
    var fromAccount: String? = null,
    var toAccount: String? = null,
    var period: Period? = null
) {
    companion object {
        val PERIOD_TYPE_MONTHLY = "monthly"
        val PERIOD_TYPE_WEEKLY = "weekly"
    }
}