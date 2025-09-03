package com.asdf.bookpediamodulasi

import android.app.Application
import com.asdf.bookpediamodulasi.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookApplication)
        }
    }
}