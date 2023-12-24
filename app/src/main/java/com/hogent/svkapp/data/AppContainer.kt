package com.hogent.svkapp.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.hogent.svkapp.R
import com.hogent.svkapp.data.repositories.CargoTicketApiRepositoryImpl
import com.hogent.svkapp.data.repositories.CargoTicketLocalRepositoryImpl
import com.hogent.svkapp.data.repositories.MainCargoTicketRepository
import com.hogent.svkapp.data.repositories.MainCargoTicketRepositoryImpl
import com.hogent.svkapp.data.repositories.UserApiRepository
import com.hogent.svkapp.data.repositories.UserApiRepositoryImpl
import com.hogent.svkapp.data.sources.roomDataBase.AppDatabase
import com.hogent.svkapp.data.sources.roomDataBase.UnprocessedCargoTicketsManager
import com.hogent.svkapp.network.CargoTicketApiService
import com.hogent.svkapp.network.UserApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * The container of the application.
 *
 * @property mainCargoTicketRepository the [MainCargoTicketRepository] that is used to save CargoTickets.
 * @property userApiRepository the [UserApiRepository] that is used to save Users.
 */
interface AppContainer {
    val mainCargoTicketRepository: MainCargoTicketRepository
    val userApiRepository: UserApiRepository
}

/**
 * The default implementation of the [AppContainer].
 *
 * @property context the application context.
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = context.getString(R.string.base_url)

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        val token = getTokenFromSecureStorage()

        val newRequest = originalRequest.newBuilder().header("Authorization", "Bearer $token").build()

        chain.proceed(newRequest)
    }

    private fun getTokenFromSecureStorage(): String {
        val sharedPreferences = context.getSharedPreferences("AuthPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("auth0_token", "") ?: ""
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("RetrofitLog", message)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun getSafeOkHttpClient(): OkHttpClient {
        val trustAllCerts = arrayOf<TrustManager>(@SuppressLint("CustomX509TrustManager") object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(
                chain: Array<out X509Certificate>?, authType: String?
            ) {
                // Trust all client certificates
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(
                chain: Array<out X509Certificate>?, authType: String?
            ) {
                // Trust all server certificates
            }

            override fun getAcceptedIssuers(): Array<out X509Certificate> {
                return arrayOf()
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        val trustManagerFactory: TrustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)

        val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers

        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            "Unexpected default trust managers:" + trustManagers.contentToString()
        }

        val trustManager = trustManagers[0] as X509TrustManager

        return OkHttpClient.Builder().addInterceptor(authInterceptor).addInterceptor(createLoggingInterceptor())
            .sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier { _, _ -> true } // Trust all hostnames
            .connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl).client(getSafeOkHttpClient()).build()
    }

    private val cargoTicketApiService: CargoTicketApiService by lazy {
        retrofit.create(CargoTicketApiService::class.java)
    }

    private val db = AppDatabase.getInstance(context)
    private val cargoTicketDao = db.cargoTicketDao()

    override val mainCargoTicketRepository: MainCargoTicketRepository by lazy {
        val cargoTicketApiRepository = CargoTicketApiRepositoryImpl(cargoTicketApiService)
        val cargoTicketLocalRepository = CargoTicketLocalRepositoryImpl(cargoTicketDao, context)

        MainCargoTicketRepositoryImpl(
            UnprocessedCargoTicketsManager(cargoTicketApiRepository, cargoTicketLocalRepository, context),
            cargoTicketApiRepository,
            cargoTicketLocalRepository
        )
    }

    override val userApiRepository: UserApiRepository by lazy {
        UserApiRepositoryImpl(retrofit.create(UserApiService::class.java))
    }
}
