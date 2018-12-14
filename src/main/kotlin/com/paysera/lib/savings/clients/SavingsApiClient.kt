package com.paysera.lib.savings.clients

import com.paysera.lib.savings.entities.*
import com.paysera.lib.savings.entities.requests.CreateAutomatedFillRequest
import com.paysera.lib.savings.entities.requests.CreateSavingsAccountRequest
import com.paysera.lib.savings.entities.requests.SetSavingsAccountGoalRequest
import com.paysera.lib.savings.interfaces.TokenRefresherInterface
import com.paysera.lib.savings.retrofit.APIClient
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.util.concurrent.TimeUnit.SECONDS

class SavingsApiClient(
    private val apiClient: APIClient,
    private val tokenRefresherInterface: TokenRefresherInterface
) {
    private val retryCondition = { errors: Flowable<Throwable> ->
        errors.flatMap {
            val isUnauthorized = (it as? HttpException)?.code() == 401
            if (isUnauthorized) {
                synchronized(tokenRefresherInterface) {
                    if (tokenRefresherInterface.isRefreshing()) {
                        Flowable.timer(1, SECONDS).subscribeOn(Schedulers.io())
                    } else {
                        tokenRefresherInterface.refreshToken().toFlowable()
                    }
                }
            } else {
                Flowable.error(it)
            }
        }
    }

    fun getSavingsAccount(filter: SavingsAccountFilter): Single<MetadataAwareResponse<SavingsAccount>> {
        return apiClient.getSavingsAccounts(filter.accountNumbers).retryWhen(retryCondition)
    }

    fun createSavingsAccount(userId: String, request: CreateSavingsAccountRequest): Single<SavingsAccount> {
        return apiClient.createSavingsAccount(userId, request).retryWhen(retryCondition)
    }

    fun setSavingsAccountGoal(
        accountNumber: String,
        request: SetSavingsAccountGoalRequest
    ): Single<SavingsAccountGoal> {
        return apiClient.setSavingsAccountGoal(accountNumber, request).retryWhen(retryCondition)
    }

    fun deleteSavingsAccountGoal(accountNumber: String): Single<Unit> {
        return apiClient.deleteSavingsAccountGoal(accountNumber).retryWhen(retryCondition)
            .flatMap {
                if (it.code() == 204) {
                    Single.just(Unit)
                } else {
                    Single.error(HttpException(it))
                }
            }
    }

    fun createAutomatedFill(
        request: CreateAutomatedFillRequest
    ): Single<AutomatedFill> {
        return apiClient.createAutomatedFill(request.toAccount!!, request).retryWhen(retryCondition)
    }

    fun getAutomatedFills(filter: AutomatedFillsFilter): Single<MetadataAwareResponse<AutomatedFill>> {
        return apiClient.getAutomatedFills(filter.toAccountNumbers).retryWhen(retryCondition)
    }

    fun getAutomatedFill(id: String): Single<AutomatedFill> {
        return apiClient.getAutomatedFill(id).retryWhen(retryCondition)
    }

    fun cancelAutomatedFill(id: String): Single<Unit> {
        return apiClient.cancelAutomatedFill(id).retryWhen(retryCondition)
            .flatMap {
                if (it.code() == 204) {
                    Single.just(Unit)
                } else {
                    Single.error(HttpException(it))
                }
            }
    }
}