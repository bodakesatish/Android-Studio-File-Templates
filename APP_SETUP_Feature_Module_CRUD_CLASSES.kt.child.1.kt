package ${PACKAGE_NAME}.data.di

import android.content.Context
import androidx.room.Room
import ${PACKAGE_NAME}.data.source.local.database.${NAME}Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "${NAME.toLowerCase()}app.db"

    @Provides
    @Singleton
    fun provides${NAME}Database(@ApplicationContext appContext: Context): ${NAME}Database {
        return Room.databaseBuilder(
            appContext,
            ${NAME}Database::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provides${NAME}Dao(database: ${NAME}Database) = database.${NAME.toLowerCase()}Dao()

}