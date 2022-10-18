package com.enraher.githublist.data.remoto

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CommonApiInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(setInterceptorRequest(chain))

    private fun setInterceptorRequest(chain: Interceptor.Chain): Request {
        synchronized(this) {
            return chain.request().newBuilder().build()
        }
    }
}
