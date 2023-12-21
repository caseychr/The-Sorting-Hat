package com.casey.thesortinghat.di

import android.app.Application
import com.casey.thesortinghat.di.AppComponent
import com.casey.thesortinghat.di.DaggerAppComponent

class TheSortingHatApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().application(this).build()
    }
}