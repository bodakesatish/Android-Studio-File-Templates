package ${PACKAGE_NAME}.data.source.local.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow

import ${PACKAGE_NAME}.data.source.local.database.entity.Sub${NAME}Entity

@Dao
interface Sub${NAME}Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateSub${NAME}(sub${NAME}: Sub${NAME}Entity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateSub${NAME}List(sub${NAME}List: List<Sub${NAME}Entity>): List<Long>

    @Query("SELECT * FROM sub${NAME.toLowerCase()} WHERE uid = :uid LIMIT 1")
    fun observeSub${NAME}ById(uid: String): Flow<Sub${NAME}Entity?>

    @Query("SELECT * FROM sub${NAME.toLowerCase()} WHERE uid = :uid LIMIT 1")
    suspend fun getSub${NAME}ById(uid: String): Sub${NAME}Entity?

    @Query("DELETE FROM sub${NAME.toLowerCase()} WHERE uid = :uid")
    suspend fun deleteSub${NAME}ById(uid: String): Int

    @Query("DELETE FROM sub${NAME.toLowerCase()} WHERE ${NAME.toLowerCase()}Id = :${NAME.toLowerCase()}Id")
    suspend fun deleteAllSub${NAME}s(${NAME.toLowerCase()}Id: String): Int

    @Query("SELECT * FROM sub${NAME.toLowerCase()} WHERE ${NAME.toLowerCase()}Id = :${NAME.toLowerCase()}Id")
    fun observeAllSub${NAME}s(${NAME.toLowerCase()}Id: String): Flow<List<Sub${NAME}Entity>>

    @Query("SELECT * FROM sub${NAME.toLowerCase()}")
    suspend fun getAllSub${NAME}s(): List<Sub${NAME}Entity>

    @Query("SELECT COUNT(*) FROM sub${NAME.toLowerCase()} WHERE ${NAME.toLowerCase()}Id = :${NAME.toLowerCase()}Id")
    fun getSub${NAME}Count(${NAME.toLowerCase()}Id: String): Int

}