package com.paysera.lib.savings.serializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.lang.reflect.Type

class MoneyDeserializer : JsonDeserializer<Money> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Money {
        val jsonObject = json?.asJsonObject
        val amount = jsonObject?.get("amount")?.asBigDecimal
        val currency = jsonObject?.get("currency")?.asString
        return Money.of(CurrencyUnit.of(currency), amount)
    }

}