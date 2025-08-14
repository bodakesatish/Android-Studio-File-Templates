package ${PACKAGE_NAME}.data.source.local

import ${PACKAGE_NAME}.domain.model.${NAME}
import kotlinx.coroutines.flow.Flow

interface Sub${NAME}LocalDataSource {
    suspend fun createOrUpdateLocalSub${NAME}(sub${NAME}: Sub${NAME}): Long
    suspend fun saveLocalSub${NAME}List(sub${NAME}List: List<Sub${NAME}>): List<Long>
    suspend fun deleteLocalSub${NAME}(uid: String): Int
    suspend fun deleteAllLocalSub${NAME}(${NAME.toLowerCase()}Id: String): Int
    suspend fun getLocalSub${NAME}ById(uid: String): Sub${NAME}?
    fun observeLocalSub${NAME}ById(uid: String): Flow<Sub${NAME}?>
    fun observeLocalSub${NAME}s(${NAME.toLowerCase()}Id: String): Flow<List<Sub${NAME}>>
    suspend fun getLocalSub${NAME}s(): List<Sub${NAME}>
    suspend fun isLocalCacheEmpty(${NAME.toLowerCase()}Id: String): Boolean // A helper to check if cache is empty
}