package pe.com.bcp.guidelineunittest.di.module

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import pe.com.bcp.guidelineunittest.App
import pe.com.bcp.guidelineunittest.presentation.details.UserDetailsFragment
import pe.com.bcp.guidelineunittest.presentation.users.UsersFragment
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelModule::class,
        NetworkModule::class,
        DataModule::class,
    ]
)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun provideContext(application: App): Context

    @ContributesAndroidInjector
    abstract fun contributeUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    abstract fun contributeUserDetailsFragment(): UserDetailsFragment
}