package com.hogent.svkapp.data

import android.annotation.SuppressLint
import android.content.Context
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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.security.KeyStore
import java.security.SecureRandom
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
    private val retrofit = getUnsafeOkHttpClient()?.let {
        Retrofit.Builder().addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        ).baseUrl(BASE_URL).client(it).build()
    }

    private val cargoTicketApiService: CargoTicketApiService by lazy {
        retrofit!!.create(CargoTicketApiService::class.java)
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
        UserApiRepository(retrofit!!.create(UserApiService::class.java))
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient? {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts =
                arrayOf<TrustManager>(@SuppressLint("CustomX509TrustManager") object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkClientTrusted(
                        chain: Array<out java.security.cert.X509Certificate>?, authType: String?
                    ) {

                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkServerTrusted(
                        chain: Array<out java.security.cert.X509Certificate>?, authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<out java.security.cert.X509Certificate> {
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


            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustManager)
            builder.hostnameVerifier { _, _ -> true }
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}