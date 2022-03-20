package com.kailin.coroutines_arch.utils.connect

import com.kailin.coroutines_arch.BuildConfig
import com.kailin.coroutines_arch.utils.GsonHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object ConnectHelper {

    val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .addNetworkInterceptor(OkHttpInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(BuildConfig.OKHTTP_CONECT_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            try {
                val x509TrustManager = object : X509TrustManager {
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                    }

                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf(x509TrustManager), SecureRandom())
                builder.sslSocketFactory(sslContext.socketFactory, x509TrustManager)
                builder.hostnameVerifier { _, _ -> true }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return@lazy builder.build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.OKHTTP_BASEURL)
            .addConverterFactory(GsonConverterFactory.create(GsonHelper.gson))
            .build()
    }

    fun <T> createService(service: Class<T>): T = retrofit.create(service)
}