package ${PACKAGE_NAME}.data.source.remote

import ${PACKAGE_NAME}.domain.utils.NetworkResult
import ${PACKAGE_NAME}.domain.model.${NAME}
import kotlinx.coroutines.flow.Flow

interface ${NAME}RemoteDataSource {
    suspend fun createRemote${NAME}(${NAME.toLowerCase()}: ${NAME}): Flow<NetworkResult<${NAME}>>
    suspend fun updateRemote${NAME}(${NAME.toLowerCase()}: ${NAME}): Flow<NetworkResult<${NAME}>>
    suspend fun deleteRemote${NAME}(uid: String): Flow<NetworkResult<Boolean>>
    suspend fun getRemote${NAME}s(): Flow<NetworkResult<List<${NAME}>>>
}