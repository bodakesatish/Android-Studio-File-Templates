package ${PACKAGE_NAME}.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher // Example for another dispatcher

@Module
@InstallIn(SingletonComponent::class) // Or another appropriate component
object DispatcherModule {

    @Provides
    @IoDispatcher // Create a custom qualifier if you have multiple dispatchers
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher // Apply a different qualifier for another dispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}