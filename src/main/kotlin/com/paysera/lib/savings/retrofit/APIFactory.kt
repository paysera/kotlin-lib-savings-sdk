package com.paysera.lib.savings.retrofit

import com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
import com.google.gson.GsonBuilder
import com.paysera.lib.savings.clients.SavingsApiClient
import com.paysera.lib.savings.entities.SavingsApiCredentials
import com.paysera.lib.savings.interfaces.TokenRefresherInterface
import com.paysera.lib.savings.serializers.MoneyDeserializer
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.joda.money.Money
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIFactory(private val credentials: SavingsApiCredentials) {
    fun createClient(
        tokenRefresher: TokenRefresherInterface,
        baseUrl: String = "https://savings.paysera.com/savings/rest/v1/"
    ): SavingsApiClient {
        return SavingsApiClient(createRetrofitClient(baseUrl), tokenRefresher)
    }

    private fun createRetrofitClient(baseUrl: String): APIClient {
        return createRetrofit(baseUrl).create(APIClient::class.java)
    }

    private fun createRetrofit(baseUrl: String) = with(Retrofit.Builder()) {
        baseUrl(baseUrl)
        addConverterFactory(createGsonConverterFactory())
        addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        client(createOkHttpClient())
        build()
    }

    private fun createOkHttpClient() = with(OkHttpClient().newBuilder()) {
        addInterceptor { chain ->
            val originalRequest = chain.request()
            val builder =
                originalRequest.newBuilder().header("Authorization", "Bearer ${credentials.accessToken}")
            val modifiedRequest = builder.build()
            chain.proceed(modifiedRequest)
        }
        build()
    }

    private fun createGsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
        gsonBuilder.registerTypeAdapter(Money::class.java, MoneyDeserializer())
        return GsonConverterFactory.create(gsonBuilder.create())
    }
}