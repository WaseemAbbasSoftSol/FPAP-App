package com.ilmkidunya.fpap

import android.app.Application
import com.ilmkidunya.fpap.di.repositoriesModule
import com.ilmkidunya.fpap.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FPAP_App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FPAP_App)
            modules(listOf(viewModelsModule, repositoriesModule))
        }
    }

}