package pe.com.bcp.guidelineunittest

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import pe.com.bcp.guidelineunittest.di.component.DaggerApplicationComponent
import timber.log.Timber

open class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?>? {
        return DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}