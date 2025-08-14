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
interface ${NAME}DataSourceModule {

    @Singleton
    @Binds
    fun binds${NAME}LocalDataSource(
        ${NAME.toLowerCase()}LocalDataSource: ${NAME}LocalDataSourceImpl
    ) : ${NAME}LocalDataSource

    @Singleton
    @Binds
    fun binds${NAME}RemoteDataSource(
        ${NAME.toLowerCase()}RemoteDataSource: ${NAME}RemoteDataSourceImpl
    ) : ${NAME}RemoteDataSource

}