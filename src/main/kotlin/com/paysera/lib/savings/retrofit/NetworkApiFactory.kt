package com.paysera.lib.savings.retrofit

import com.paysera.lib.common.entities.ApiCredentials
import com.paysera.lib.common.interfaces.ErrorLoggerInterface
import com.paysera.lib.common.interfaces.TokenRefresherInterface
import com.paysera.lib.common.retrofit.BaseApiFactory
import com.paysera.lib.savings.clients.SavingsApiClient
import okhttp3.logging.HttpLoggingInterceptor

class NetworkApiFactory(
    userAgent: String?,
    credentials: ApiCredentials,
    timeout: Long? = null,
    httpLoggingInterceptorLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC,
    errorLoggerInterface: ErrorLoggerInterface
) : BaseApiFactory<SavingsApiClient>(
    userAgent,
    credentials,
    timeout,
    httpLoggingInterceptorLevel,
    errorLoggerInterface
) {
    override val baseUrl = "https://savings.paysera.com/savings/rest/v1/"
    override val certifiedHosts = listOf("savings.paysera.com")

    override fun createClient(tokenRefresher: TokenRefresherInterface?): SavingsApiClient {
        createRetrofit(tokenRefresher).apply {
            return SavingsApiClient(
                retrofit.create(NetworkApiClient::class.java),
                apiRequestManager
            )
        }
    }
}