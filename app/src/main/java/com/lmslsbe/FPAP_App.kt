package com.lmslsbe

import android.app.Application
import com.lmslsbe.di.repositoriesModule
import com.lmslsbe.di.viewModelsModule
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