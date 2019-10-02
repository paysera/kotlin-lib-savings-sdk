package com.paysera.lib.savings.entities

data class SavingsAccount(
    val type: String,
    val accountNumber: String,
    val goal: SavingsAccountGoal?,
    val createdBy: String,
    val displayUrl: String?
) {
    companion object {
        val TYPE_PRIVATE = "private"
        val TYPE_PUBLIC = "public"
    }
}