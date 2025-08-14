package ${PACKAGE_NAME}.data.di

import ${PACKAGE_NAME}.data.repository.${NAME}RepositoryImpl
import ${PACKAGE_NAME}.domain.repository.${NAME}Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ${NAME}RepositoryModule {

    @Binds
    @Singleton
    fun bind${NAME}Repository(${NAME.toLowerCase()}Repository: ${NAME}RepositoryImpl): ${NAME}Repository

}