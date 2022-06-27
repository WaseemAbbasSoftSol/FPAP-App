package com.softsolutions.fpap.di

import com.softsolutions.fpap.R
import com.softsolutions.fpap.data.FpapApi
import com.softsolutions.fpap.data.FpapRepository
import com.softsolutions.fpap.data.OAuthInterceptor
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.ui.account.AccountViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = ""

val viewModelsModule= module {
    viewModel { AccountViewModel() }
    }

  val repositoriesModule = module {

    fun provideHttpClient(oAuthInterceptor: OAuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(oAuthInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    fun createApi(factory: GsonConverterFactory, client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(factory)
        .client(client)
        .build()
        .create(FpapApi::class.java)
    single { OAuthInterceptor(androidContext().resources.getString(R.string.access_token)) }
    single { provideHttpClient(get()) }
    single { GsonConverterFactory.create() }
    single { createApi(get(), get()) }
    single { FpapRepository(get()) }
    single { PrefRepository(androidApplication()) }
}
