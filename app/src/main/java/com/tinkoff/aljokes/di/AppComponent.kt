package com.tinkoff.aljokes.di

import android.content.Context
import com.tinkoff.aljokes.MainActivity
import com.tinkoff.aljokes.di.module.DataModule
import com.tinkoff.aljokes.di.module.DomainModule
import com.tinkoff.aljokes.di.module.PresentationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}