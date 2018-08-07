package com.paysera.lib.savings.retrofit

import com.paysera.lib.savings.entities.AutomatedFill
import com.paysera.lib.savings.entities.SavingsAccount
import com.paysera.lib.savings.entities.SavingsAccountGoal
import com.paysera.lib.savings.entities.requests.CreateAutomatedFillRequest
import com.paysera.lib.savings.entities.requests.CreateSavingsAccountRequest
import com.paysera.lib.savings.entities.requests.SetSavingsAccountGoalRequest
import io.reactivex.Observable
import retrofit2.http.*

interface APIClient {
    @GET("savings-accounts")
    fun getSavingsAccounts(
        @Query("account_numbers[]") accountNumbers: List<String>
    ): Observable<List<SavingsAccount>>

    @POST("users/{userId}/savings-accounts")
    fun createSavingsAccount(
        @Path("userId") userId: String,
        @Body createSavingsAccountRequest: CreateSavingsAccountRequest
    ): Observable<SavingsAccount>

    @PUT("savings-accounts/{accountNumber}/goal")
    fun setSavingsAccountGoal(
        @Path("accountNumber") accountNumber: String,
        @Body request: SetSavingsAccountGoalRequest
    ): Observable<SavingsAccountGoal>

    @DELETE("savings-accounts/{accountNumber}/goal")
    fun deleteSavingsAccountGoal(accountNumber: String): Observable<Unit>

    @POST("savings-account/{accountNumber}/automated-fills")
    fun createAutomatedFill(
        @Path("accountNumber") accountNumber: String,
        @Body createAutomatedFill: CreateAutomatedFillRequest
    ): Observable<AutomatedFill>

    @GET("automated-fills")
    fun getAutomatedFills(
        @Query("to_account_numbers[]") toAccountNumbers: List<String>
    ): Observable<List<AutomatedFill>>

    @GET("automated-fills/{id}")
    fun getAutomatedFill(
        @Path("id") id: String
    ): Observable<AutomatedFill>

    @DELETE("automated-fills/{id}")
    fun cancelAutomatedFill(
        @Path("id") id: String
    ): Observable<Unit>
}