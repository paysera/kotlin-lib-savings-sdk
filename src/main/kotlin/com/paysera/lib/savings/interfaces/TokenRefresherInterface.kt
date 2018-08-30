package com.paysera.lib.savings.interfaces

import io.reactivex.Single

interface TokenRefresherInterface {
    fun refreshToken(): Single<Any>
    fun isRefreshing(): Boolean
}