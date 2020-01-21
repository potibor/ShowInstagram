package com.hsnozan.basemvvm

import android.app.Application
import android.content.Context
import com.hsnozan.basemvvm.di.networkModule
import com.hsnozan.basemvvm.di.repoModule
import com.hsnozan.basemvvm.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class BaseApplication : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger()
            modules(listOf(networkModule, repoModule, viewModelModule))
        }
    }
}