package ${PACKAGE_NAME}.data.source.local

import ${PACKAGE_NAME}.domain.model.${NAME}
import kotlinx.coroutines.flow.Flow

interface ${NAME}LocalDataSource {
    suspend fun createOrUpdateLocal${NAME}(${NAME.toLowerCase()}: ${NAME}): Long
    suspend fun saveLocal${NAME}List(${NAME.toLowerCase()}List: List<${NAME}>): List<Long>
    suspend fun deleteLocal${NAME}(uid: String): Int
    suspend fun deleteAllLocal${NAME}(): Int
    suspend fun getLocal${NAME}ById(uid: String): ${NAME}?
    fun observeLocal${NAME}ById(uid: String): Flow<${NAME}?>
    fun observeLocal${NAME}s(): Flow<List<${NAME}>>
    suspend fun getLocal${NAME}s(): List<${NAME}>
    suspend fun isLocalCacheEmpty(): Boolean // A helper to check if cache is empty
}