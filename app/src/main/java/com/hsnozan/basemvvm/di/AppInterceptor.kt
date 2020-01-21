package com.hsnozan.basemvvm.di

import com.hsnozan.basemvvm.utils.Constants
import com.hsnozan.basemvvm.utils.Preference_ApplicationData
import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor(private val applicationData: Preference_ApplicationData) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val requestBuilder = request.newBuilder()
        val longLivedToken = applicationData.longLivedAccessToken
        val accessToken = applicationData.accessToken

        request = requestBuilder.build()
        if (request.url.toString().contains(Constants.BASE_URL_PROFILE)) {
            var serverUrlData = ""
            serverUrlData += if (!longLivedToken.isNullOrEmpty()) {
                "&access_token=$longLivedToken"
            } else {
                "&access_token=$accessToken"
            }
            val url = request.url.toString()
            request = request.newBuilder().url(url.plus(serverUrlData)).build()
        }

        return chain.proceed(request)
    }
}