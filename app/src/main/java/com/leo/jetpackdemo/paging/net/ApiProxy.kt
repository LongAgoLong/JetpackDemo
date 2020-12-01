package com.leo.jetpackdemo.paging.net

import android.text.TextUtils
import android.util.Log
import com.leo.okhttplib.dns.TimeoutDNS
import com.leo.okhttplib.interceptor.LogIntercepter
import com.leo.okhttplib.interceptor.RetryIntercepter
import com.leo.okhttplib.ssl.HttpSSLUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.Proxy
import java.util.*
import java.util.concurrent.TimeUnit


class ApiProxy private constructor() {
    private var api: ApiServer

    init {
        val unSafeConnectionSpecs = HttpSSLUtils.getUnSafeConnectionSpecs()
        val sslParams = HttpSSLUtils.getSslSocketFactory(null, null, null)
        val okHttpClient = OkHttpClient.Builder()
            .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .connectionSpecs(unSafeConnectionSpecs)
            .dns(TimeoutDNS(3, TimeUnit.SECONDS))
            .retryOnConnectionFailure(true)
            .addInterceptor(RetryIntercepter(2))
            .addInterceptor(LogIntercepter("OKHTTP", false))
            .addInterceptor(MoreBaseUrlInterceptor())
            .proxy(Proxy.NO_PROXY)
            //其他配置
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_FLAG)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
        api = retrofit.create(ApiServer::class.java)
    }

    /**
     * 拦截替换BASE-URL
     */
    private inner class MoreBaseUrlInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            // 获取原始的originalRequest
            val originalRequest = chain.request()
            // 获取当前的url
            val oldUrl = originalRequest.url()
            val oldUrlStr = oldUrl.url().toString()
            return if (oldUrlStr.contains(BASE_URL_FLAG, true)) {
                val newUrl =
                    oldUrlStr.replace(BASE_URL_FLAG.toLowerCase(Locale.getDefault()), BASE_URL)
                //获取originalRequest的创建者builder
                val newRequest = originalRequest.newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(originalRequest)
            }
        }
    }

    companion object {
        private const val BASE_URL_FLAG = "https://BASE_URL_FLAG/"
        private const val BASE_URL = "https://www.wanandroid.com/"

        private var mInstance: ApiProxy? = null
        fun getInstance(): ApiProxy {
            return mInstance ?: synchronized(this) {
                mInstance ?: ApiProxy().also { mInstance = it }
            }
        }
    }

    /**
     * 查看某个公众号历史数据
     */
    suspend fun getHistoricalData(page: Int): Response<String> = withContext(Dispatchers.IO) {
        api.getHistoricalData(408, page).execute()
    }
}