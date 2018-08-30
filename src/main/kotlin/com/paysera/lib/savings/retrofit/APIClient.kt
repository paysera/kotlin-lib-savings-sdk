package com.paysera.lib.savings.retrofit

import com.paysera.lib.savings.entities.AutomatedFill
import com.paysera.lib.savings.entities.SavingsAccount
import com.paysera.lib.savings.entities.SavingsAccountGoal
import com.paysera.lib.savings.entities.requests.CreateAutomatedFillRequest
import com.paysera.lib.savings.entities.requests.CreateSavingsAccountRequest
import com.paysera.lib.savings.entities.requests.SetSavingsAccountGoalRequest
import io.reactivex.Single
import retrofit2.http.*

interface APIClient {
    @GET("savings-accounts")
    fun getSavingsAccounts(
        @Query("account_numbers[]") accountNumbers: List<String>
    ): Single<List<SavingsAccount>>

    @POST("users/{userId}/savings-accounts")
    fun createSavingsAccount(
        @Path("userId") userId: String,
        @Body createSavingsAccountRequest: CreateSavingsAccountRequest
    ): Single<SavingsAccount>

    @PUT("savings-accounts/{accountNumber}/goal")
    fun setSavingsAccountGoal(
        @Path("accountNumber") accountNumber: String,
        @Body request: SetSavingsAccountGoalRequest
    ): Single<SavingsAccountGoal>

    @DELETE("savings-accounts/{accountNumber}/goal")
    fun deleteSavingsAccountGoal(accountNumber: String): Single<Unit>

    @POST("savings-account/{accountNumber}/automated-fills")
    fun createAutomatedFill(
        @Path("accountNumber") accountNumber: String,
        @Body createAutomatedFill: CreateAutomatedFillRequest
    ): Single<AutomatedFill>

    @GET("automated-fills")
    fun getAutomatedFills(
        @Query("to_account_numbers[]") toAccountNumbers: List<String>
    ): Single<List<AutomatedFill>>

    @GET("automated-fills/{id}")
    fun getAutomatedFill(
        @Path("id") id: String
    ): Single<AutomatedFill>

    @DELETE("automated-fills/{id}")
    fun cancelAutomatedFill(
        @Path("id") id: String
    ): Single<Unit>
}