package com.paysera.lib.savings.interfaces

import io.reactivex.Observable

interface TokenRefresherInterface {
    fun refreshToken(): Observable<Any>
    fun isRefreshing(): Boolean
}