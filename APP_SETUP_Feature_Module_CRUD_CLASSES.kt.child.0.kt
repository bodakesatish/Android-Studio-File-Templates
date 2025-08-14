package ${PACKAGE_NAME}.domain.repository

import ${PACKAGE_NAME}.domain.utils.NetworkResult
import ${PACKAGE_NAME}.domain.model.${NAME}
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ${NAME}Repository {
    suspend fun create${NAME}(${NAME.toLowerCase()}: ${NAME}): Flow<NetworkResult<${NAME}>>
    suspend fun update${NAME}(${NAME.toLowerCase()}: ${NAME}): Flow<NetworkResult<${NAME}>>
    suspend fun delete${NAME}(uid: String): Flow<NetworkResult<Boolean>>
    suspend fun get${NAME}s(isForcedRefresh: Boolean = false): Flow<NetworkResult<List<${NAME}>>>
    suspend fun observe${NAME}ById(uid: String): Flow<NetworkResult<${NAME}>>
}