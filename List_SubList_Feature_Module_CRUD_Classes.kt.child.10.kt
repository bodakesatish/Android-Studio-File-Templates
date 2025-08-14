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
interface Sub${NAME}RepositoryModule {

    @Binds
    @Singleton
    fun bindSub${NAME}Repository(sub${NAME}Repository: Sub${NAME}RepositoryImpl): Sub${NAME}Repository

}