package ${PACKAGE_NAME}.data.di

import android.content.Context
import ${PACKAGE_NAME}.data.source.remote.utils.NetworkConnectivityService
import ${PACKAGE_NAME}.data.source.remote.utils.NetworkConnectivityServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConnectivityModule {

    @Provides
    @Singleton
    fun provideNetworkConnectivityService(
        @ApplicationContext context: Context
    ): NetworkConnectivityService {
        return NetworkConnectivityServiceImpl(context)
    }

}
