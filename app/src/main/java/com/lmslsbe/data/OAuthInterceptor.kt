package com.lmslsbe.data

import okhttp3.Interceptor

class OAuthInterceptor(private val accessToken: String) :
    Interceptor {
    private val tokenType = "Bearer"
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request =
            request.newBuilder().header("Authorization", accessToken).build()
        return chain.proceed(request)
    }
}