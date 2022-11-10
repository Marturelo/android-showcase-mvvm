package pe.com.bcp.guidelineunittest

import android.app.Application
import pe.com.bcp.guidelineunittest.di.Locator
import pe.com.bcp.guidelineunittest.ext.create
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Locator.create(this)
    }
}