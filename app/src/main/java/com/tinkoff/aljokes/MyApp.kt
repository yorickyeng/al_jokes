package com.tinkoff.aljokes

import android.app.Application
import com.tinkoff.aljokes.di.AppComponent
import com.tinkoff.aljokes.di.DaggerAppComponent

class MyApp:Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}