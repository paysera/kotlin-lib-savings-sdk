package com.paysera.lib.savings.retrofit

import com.paysera.lib.common.entities.MetadataAwareResponse
import com.paysera.lib.savings.entities.AutomatedFill
import com.paysera.lib.savings.entities.SavingsAccount
import com.paysera.lib.savings.entities.SavingsAccountGoal
import com.paysera.lib.savings.entities.requests.CreateAutomatedFillRequest
import com.paysera.lib.savings.entities.requests.CreateSavingsAccountRequest
import com.paysera.lib.savings.entities.requests.SetSavingsAccountGoalRequest
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface NetworkApiClient {

    @GET("savings-accounts")
    fun getSavingsAccounts(
        @Query("account_numbers[]") accountNumbers: List<String>
    ): Deferred<MetadataAwareResponse<SavingsAccount>>

    @POST("users/{userId}/savings-accounts")
    fun createSavingsAccount(
        @Path("userId") userId: String,
        @Body request: CreateSavingsAccountRequest
    ): Deferred<SavingsAccount>

    @PUT("savings-accounts/{accountNumber}/goal")
    fun setSavingsAccountGoal(
        @Path("accountNumber") accountNumber: String,
        @Body request: SetSavingsAccountGoalRequest
    ): Deferred<SavingsAccountGoal>

    @DELETE("savings-accounts/{accountNumber}/goal")
    fun deleteSavingsAccountGoal(
        @Path("accountNumber") accountNumber: String
    ): Deferred<Void>

    @POST("savings-accounts/{accountNumber}/automated-fills")
    fun createAutomatedFill(
        @Path("accountNumber") accountNumber: String,
        @Body createAutomatedFill: CreateAutomatedFillRequest
    ): Deferred<AutomatedFill>

    @GET("automated-fills")
    fun getAutomatedFills(
        @Query("to_account_numbers[]") toAccountNumbers: List<String>
    ): Deferred<MetadataAwareResponse<AutomatedFill>>

    @GET("automated-fills/{id}")
    fun getAutomatedFill(
        @Path("id") id: String
    ): Deferred<AutomatedFill>

    @DELETE("automated-fills/{id}")
    fun cancelAutomatedFill(
        @Path("id") id: String
    ): Deferred<Void>
}