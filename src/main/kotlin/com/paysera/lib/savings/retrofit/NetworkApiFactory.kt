package com.paysera.lib.savings.retrofit

import com.paysera.lib.common.entities.ApiCredentials
import com.paysera.lib.common.interfaces.TokenRefresher
import com.paysera.lib.common.retrofit.BaseRefreshingApiFactory
import com.paysera.lib.savings.clients.SavingsApiClient

class NetworkApiFactory(credentials: ApiCredentials) : BaseRefreshingApiFactory<SavingsApiClient>(credentials) {

    override fun createClient(tokenRefresherInterface: TokenRefresher, baseUrl: String): SavingsApiClient {
        return SavingsApiClient(
            createRetrofit(baseUrl).create(NetworkApiClient::class.java),
            tokenRefresherInterface
        )
    }
}