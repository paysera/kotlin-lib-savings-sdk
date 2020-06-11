package com.paysera.lib.savings.clients

import com.paysera.lib.common.entities.MetadataAwareResponse
import com.paysera.lib.common.retrofit.ApiRequestManager
import com.paysera.lib.common.retrofit.BaseApiClient
import com.paysera.lib.savings.entities.*
import com.paysera.lib.savings.entities.requests.CreateAutomatedFillRequest
import com.paysera.lib.savings.entities.requests.CreateSavingsAccountRequest
import com.paysera.lib.savings.entities.requests.SetSavingsAccountGoalRequest
import com.paysera.lib.savings.retrofit.NetworkApiClient
import kotlinx.coroutines.Deferred
import retrofit2.Response

class SavingsApiClient(
    private val networkApiClient: NetworkApiClient,
    apiRequestManager: ApiRequestManager
) : BaseApiClient(apiRequestManager) {

    fun getSavingsAccounts(filter: SavingsAccountFilter): Deferred<MetadataAwareResponse<SavingsAccount>>{
        return networkApiClient.getSavingsAccounts(
            accountNumbers = filter.accountNumbers
        )
    }

    fun createSavingsAccount(userId: String, request: CreateSavingsAccountRequest): Deferred<SavingsAccount> {
        return networkApiClient.createSavingsAccount(
            userId = userId,
            request = request
        )
    }

    fun setSavingsAccountGoal(accountNumber: String, request: SetSavingsAccountGoalRequest): Deferred<SavingsAccountGoal> {
        return networkApiClient.setSavingsAccountGoal(
            accountNumber = accountNumber,
            request = request
        )
    }

    fun deleteSavingsAccountGoal(accountNumber: String): Deferred<Response<Void>> {
        return networkApiClient.deleteSavingsAccountGoal(
            accountNumber = accountNumber
        )
    }

    fun createAutomatedFill(
        createAutomatedFill: CreateAutomatedFillRequest
    ): Deferred<AutomatedFill> {
        return networkApiClient.createAutomatedFill(
            accountNumber = createAutomatedFill.toAccount!!,
            createAutomatedFill = createAutomatedFill
        )
    }

    fun getAutomatedFills(filter: AutomatedFillsFilter): Deferred<MetadataAwareResponse<AutomatedFill>> {
        return networkApiClient.getAutomatedFills(
            toAccountNumbers = filter.toAccountNumbers
        )
    }

    fun getAutomatedFill(id: String): Deferred<AutomatedFill> {
        return networkApiClient.getAutomatedFill(
            id = id
        )
    }

    fun cancelAutomatedFill(id: String): Deferred<Response<Void>> {
        return networkApiClient.cancelAutomatedFill(
            id  = id
        )
    }
}