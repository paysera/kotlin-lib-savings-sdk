package com.paysera.lib.savings.retrofit

import com.paysera.lib.common.entities.AuthorizationApiCredentials
import com.paysera.lib.common.interfaces.ErrorLoggerInterface
import com.paysera.lib.common.interfaces.TokenRefresherInterface
import com.paysera.lib.common.retrofit.BaseApiFactory
import com.paysera.lib.savings.clients.SavingsApiClient
import okhttp3.logging.HttpLoggingInterceptor

class NetworkApiFactory(
    baseUrl: String,
    locale: String?,
    userAgent: String?,
    credentials: AuthorizationApiCredentials,
    timeout: Long? = null,
    httpLoggingInterceptorLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC,
    errorLogger: ErrorLoggerInterface
) : BaseApiFactory<SavingsApiClient>(
    baseUrl,
    locale,
    userAgent,
    credentials,
    timeout,
    httpLoggingInterceptorLevel,
    errorLogger
) {
    override fun createClient(tokenRefresher: TokenRefresherInterface?): SavingsApiClient {
        createRetrofit(tokenRefresher).apply {
            return SavingsApiClient(
                retrofit.create(NetworkApiClient::class.java),
                apiRequestManager
            )
        }
    }
}