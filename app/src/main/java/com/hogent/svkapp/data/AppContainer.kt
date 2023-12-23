package com.hogent.svkapp.data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.hogent.svkapp.data.repositories.CargoTicketApiRepository
import com.hogent.svkapp.data.repositories.CargoTicketLocalRepository
import com.hogent.svkapp.data.repositories.MainCargoTicketRepository
import com.hogent.svkapp.data.repositories.UserApiRepository
import com.hogent.svkapp.data.repositories.UserRepository
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
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.security.KeyStore
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

private const val BASE_URL = "https://10.0.2.2:5001/api/"

/**
 * The container of the application.
 *
 * @property cargoTicketRepository the [MainCargoTicketRepository] that is used to save CargoTickets.
 * @property userRepository the [UserRepository] that is used to save Users.
 */
interface AppContainer {
    val cargoTicketRepository: MainCargoTicketRepository
    val userRepository: UserRepository
}

/**
 * The default implementation of the [AppContainer].
 *
 * @property context the application context.
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
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
            // Log the message here (using Log.d or another logging method)
            Log.d("RetrofitLog", message)
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    // Safe OkHttpClient Configuration
    private fun getSafeOkHttpClient(): OkHttpClient {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                // Trust all client certificates
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                // Trust all server certificates
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(createLoggingInterceptor())
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true } // Trust all hostnames
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Retrofit Instance with Error Handling
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .client(getSafeOkHttpClient())
            .build()
    }

    private val cargoTicketApiService: CargoTicketApiService by lazy {
        retrofit.create(CargoTicketApiService::class.java)
    }

    private val db = AppDatabase.getInstance(context)
    private val cargoTicketDao = db.cargoTicketDao()

    override val cargoTicketRepository: MainCargoTicketRepository by lazy {
        val cargoTicketApiRepository = CargoTicketApiRepository(cargoTicketApiService)
        val cargoTicketLocalRepository = CargoTicketLocalRepository(cargoTicketDao, context)

        MainCargoTicketRepository(
            UnprocessedCargoTicketsManager(cargoTicketApiRepository, cargoTicketLocalRepository, context),
            cargoTicketApiRepository,
            cargoTicketLocalRepository
        )
    }

    override val userRepository: UserRepository by lazy {
        UserApiRepository(retrofit.create(UserApiService::class.java))
    }
}

fun <T> makeSafeApiCall(call: () -> Response<T>): T? {
    return try {
        val response = call()
        if (response.isSuccessful) {
            response.body()
        } else {
            // Log the error body
            val errorBody = response.errorBody()?.string()
            Log.e("RetrofitError", "Error occurred: $errorBody")
            null
        }
    } catch (e: IOException) {
        // Log IOException
        Log.e("RetrofitError", "IOException occurred: ${e.message}")
        null
    } catch (e: Exception) {
        // Log General Exception
        Log.e("RetrofitError", "Exception occurred: ${e.message}")
        null
    }
}