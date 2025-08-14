package ${PACKAGE_NAME}.data.di

import ${PACKAGE_NAME}.data.source.local.${NAME}LocalDataSource
import ${PACKAGE_NAME}.data.source.local.${NAME}LocalDataSourceImpl
import ${PACKAGE_NAME}.data.source.remote.${NAME}RemoteDataSource
import ${PACKAGE_NAME}.data.source.remote.${NAME}RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface Sub${NAME}DataSourceModule {

    @Singleton
    @Binds
    fun bindsSub${NAME}LocalDataSource(
       sub${NAME}LocalDataSource: Sub${NAME}LocalDataSourceImpl
    ) : Sub${NAME}LocalDataSource

    @Singleton
    @Binds
    fun bindsSub${NAME}RemoteDataSource(
        sub${NAME}RemoteDataSource: Sub${NAME}RemoteDataSourceImpl
    ) : Sub${NAME}RemoteDataSource

}