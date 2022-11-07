package pe.com.bcp.guidelineunittest.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pe.com.bcp.guidelineunittest.App
import pe.com.bcp.guidelineunittest.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
    ]
)
interface ApplicationComponent : AndroidInjector<App> {
    @Component.Factory
    interface Builder : AndroidInjector.Factory<App>
}