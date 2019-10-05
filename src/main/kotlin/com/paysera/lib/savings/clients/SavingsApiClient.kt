package com.paysera.lib.savings.clients

import com.paysera.lib.common.entities.MetadataAwareResponse
import com.paysera.lib.common.interfaces.BaseApiClient
import com.paysera.lib.common.interfaces.TokenRefresher
import com.paysera.lib.common.interfaces.retryWithTokenRefresher
import com.paysera.lib.savings.entities.*
import com.paysera.lib.savings.entities.requests.CreateAutomatedFillRequest
import com.paysera.lib.savings.entities.requests.CreateSavingsAccountRequest
import com.paysera.lib.savings.entities.requests.SetSavingsAccountGoalRequest
import com.paysera.lib.savings.retrofit.NetworkApiClient
import kotlinx.coroutines.Deferred
import retrofit2.Response

class SavingsApiClient(
    private val networkApiClient: NetworkApiClient,
    private val tokenRefresherInterface: TokenRefresher
) : BaseApiClient {

    suspend fun getSavingsAccounts(filter: SavingsAccountFilter): Deferred<MetadataAwareResponse<SavingsAccount>>{
        return networkApiClient.getSavingsAccounts(
            accountNumbers = filter.accountNumbers
        ).retryWithTokenRefresher(tokenRefresherInterface)
    }

    suspend fun createSavingsAccount(userId: String, request: CreateSavingsAccountRequest): Deferred<SavingsAccount> {
        return networkApiClient.createSavingsAccount(
            userId = userId,
            request = request
        ).retryWithTokenRefresher(tokenRefresherInterface)
    }

    suspend fun setSavingsAccountGoal(accountNumber: String, request: SetSavingsAccountGoalRequest): Deferred<SavingsAccountGoal> {
        return networkApiClient.setSavingsAccountGoal(
            accountNumber = accountNumber,
            request = request
        ).retryWithTokenRefresher(tokenRefresherInterface)
    }

    suspend fun deleteSavingsAccountGoal(accountNumber: String): Deferred<Response<Void>> {
        return networkApiClient.deleteSavingsAccountGoal(
            accountNumber = accountNumber
        ).retryWithTokenRefresher(tokenRefresherInterface)
    }

    suspend fun createAutomatedFill(
        createAutomatedFill: CreateAutomatedFillRequest
    ): Deferred<AutomatedFill> {
        return networkApiClient.createAutomatedFill(
            accountNumber = createAutomatedFill.toAccount!!,
            createAutomatedFill = createAutomatedFill
        ).retryWithTokenRefresher(tokenRefresherInterface)
    }

    suspend fun getAutomatedFills(filter: AutomatedFillsFilter): Deferred<MetadataAwareResponse<AutomatedFill>> {
        return networkApiClient.getAutomatedFills(
            toAccountNumbers = filter.toAccountNumbers
        ).retryWithTokenRefresher(tokenRefresherInterface)
    }

    suspend fun getAutomatedFill(id: String): Deferred<AutomatedFill> {
        return networkApiClient.getAutomatedFill(
            id = id
        ).retryWithTokenRefresher(tokenRefresherInterface)
    }

    suspend fun cancelAutomatedFill(id: String): Deferred<Response<Void>> {
        return networkApiClient.cancelAutomatedFill(
            id  = id
        ).retryWithTokenRefresher(tokenRefresherInterface)
    }
}