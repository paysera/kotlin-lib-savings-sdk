package com.paysera.lib.savings.retrofit

import com.paysera.lib.common.entities.PayseraApiCredentials
import com.paysera.lib.common.interfaces.ErrorLoggerInterface
import com.paysera.lib.common.interfaces.TokenRefresherInterface
import com.paysera.lib.common.retrofit.BaseApiFactory
import com.paysera.lib.savings.clients.SavingsApiClient
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

class NetworkApiFactory(
    baseUrl: String,
    locale: String?,
    userAgent: String?,
    credentials: PayseraApiCredentials,
    timeout: Long? = null,
    httpLoggingInterceptorLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BASIC,
    errorLogger: ErrorLoggerInterface,
    certificateInterceptor: Interceptor
) : BaseApiFactory<SavingsApiClient>(
    baseUrl,
    locale,
    userAgent,
    credentials,
    timeout,
    httpLoggingInterceptorLevel,
    errorLogger,
    certificateInterceptor
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