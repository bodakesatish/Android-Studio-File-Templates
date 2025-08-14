package ${PACKAGE_NAME}.data.source.local.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ${PACKAGE_NAME}.data.source.local.database.entity.${NAME}Entity

@Dao
interface ${NAME}Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate${NAME}(${NAME.toLowerCase()}: ${NAME}Entity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate${NAME}List(${NAME.toLowerCase()}List: List<${NAME}Entity>) : List<Long>

    @Query("SELECT * FROM ${NAME.toLowerCase()}s WHERE uid = :uid LIMIT 1")
    fun observe${NAME}ById(uid: String): Flow<${NAME}Entity?>

    @Query("SELECT * FROM ${NAME.toLowerCase()}s WHERE uid = :uid LIMIT 1")
    suspend fun get${NAME}ById(uid: String): ${NAME}Entity?

    @Query("DELETE FROM ${NAME.toLowerCase()}s WHERE uid = :uid")
    suspend fun delete${NAME}ById(uid: String): Int

    @Query("DELETE FROM ${NAME.toLowerCase()}s")
    suspend fun deleteAll${NAME}s(): Int

    @Query("SELECT * FROM ${NAME.toLowerCase()}s")
    fun observeAll${NAME}s(): Flow<List<${NAME}Entity>>

    @Query("SELECT * FROM ${NAME.toLowerCase()}s")
    suspend fun getAll${NAME}s(): List<${NAME}Entity>
    
    @Query("SELECT COUNT(*) FROM ${NAME.toLowerCase()}s")
    fun get${NAME}Count(): Int

}