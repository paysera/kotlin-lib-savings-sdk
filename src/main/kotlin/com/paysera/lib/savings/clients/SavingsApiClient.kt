package com.paysera.lib.savings.clients

import com.paysera.lib.savings.entities.*
import com.paysera.lib.savings.entities.requests.CreateAutomatedFillRequest
import com.paysera.lib.savings.entities.requests.CreateSavingsAccountRequest
import com.paysera.lib.savings.entities.requests.SetSavingsAccountGoalRequest
import com.paysera.lib.savings.interfaces.TokenRefresherInterface
import com.paysera.lib.savings.retrofit.APIClient
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.util.concurrent.TimeUnit.SECONDS

class SavingsApiClient(
    private val apiClient: APIClient,
    private val tokenRefresherInterface: TokenRefresherInterface
) {
    private val retryCondition = { errors: Observable<Throwable> ->
        errors.flatMap {
            val isUnauthorized = (it as HttpException).code() == 401
            if (isUnauthorized) {
                synchronized(tokenRefresherInterface) {
                    if (tokenRefresherInterface.isRefreshing()) {
                        Observable.timer(1, SECONDS).subscribeOn(Schedulers.io())
                    } else {
                        tokenRefresherInterface.refreshToken()
                    }
                }
            } else {
                Observable.error(it)
            }
        }
    }

    fun getSavingsAccount(filter: SavingsAccountFilter): Observable<List<SavingsAccount>> {
        return apiClient.getSavingsAccounts(filter.accountNumbers).retryWhen(retryCondition)
    }

    fun createSavingsAccount(userId: String, request: CreateSavingsAccountRequest): Observable<SavingsAccount> {
        return apiClient.createSavingsAccount(userId, request).retryWhen(retryCondition)
    }

    fun setSavingsAccountGoal(
        accountNumber: String,
        request: SetSavingsAccountGoalRequest
    ): Observable<SavingsAccountGoal> {
        return apiClient.setSavingsAccountGoal(accountNumber, request).retryWhen(retryCondition)
    }

    fun deleteSavingsAccountGoal(
        accountNumber: String
    ): Observable<Unit> {
        return apiClient.deleteSavingsAccountGoal(accountNumber).retryWhen(retryCondition)
    }

    fun createAutomatedFill(
        request: CreateAutomatedFillRequest
    ): Observable<AutomatedFill> {
        return apiClient.createAutomatedFill(request.toAccount!!, request).retryWhen(retryCondition)
    }

    fun getAutomatedFills(filter: AutomatedFillsFilter): Observable<List<AutomatedFill>> {
        return apiClient.getAutomatedFills(filter.toAccountNumbers).retryWhen(retryCondition)
    }

    fun getAutomatedFill(id: String): Observable<AutomatedFill> {
        return apiClient.getAutomatedFill(id).retryWhen(retryCondition)
    }

    fun cancelAutomatedFill(id: String): Observable<Unit> {
        return apiClient.cancelAutomatedFill(id).retryWhen(retryCondition)
    }
}