package ${PACKAGE_NAME}.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ${PACKAGE_NAME}.data.source.local.database.dao.${NAME}Dao
import ${PACKAGE_NAME}.data.source.local.database.entity.${NAME}Entity

@Database(entities = [${NAME}Entity::class], version = 1, exportSchema = false)
abstract class ${NAME}Database : RoomDatabase() {
    abstract fun ${NAME.toLowerCase()}Dao(): ${NAME}Dao
}
