package com.paysera.lib.savings.retrofit

import com.paysera.lib.common.entities.ApiCredentials
import com.paysera.lib.common.interfaces.TokenRefresherInterface
import com.paysera.lib.common.retrofit.BaseApiFactory
import com.paysera.lib.savings.clients.SavingsApiClient

class NetworkApiFactory(credentials: ApiCredentials, timeout: Long? = null) : BaseApiFactory<SavingsApiClient>(credentials, timeout) {

    override fun createClient(baseUrl: String, tokenRefresher: TokenRefresherInterface?): SavingsApiClient {
        createRetrofit(baseUrl, tokenRefresher).apply {
            return SavingsApiClient(
                retrofit.create(NetworkApiClient::class.java),
                apiRequestManager
            )
        }
    }
}