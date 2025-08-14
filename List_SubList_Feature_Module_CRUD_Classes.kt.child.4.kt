package ${PACKAGE_NAME}.domain.repository

import ${PACKAGE_NAME}.domain.utils.NetworkResult
import ${PACKAGE_NAME}.domain.model.Sub${NAME}
import kotlinx.coroutines.flow.Flow

interface Sub${NAME}Repository {
    suspend fun createSub${NAME}(sub${NAME}: Sub${NAME}): Flow<NetworkResult<Sub${NAME}>>
    suspend fun updateSub${NAME}(sub${NAME}: Sub${NAME}): Flow<NetworkResult<Sub${NAME}>>
    suspend fun deleteSub${NAME}(uid: String): Flow<NetworkResult<Boolean>>
    suspend fun getSub${NAME}s(${NAME.toLowerCase()}Id: String, isForcedRefresh: Boolean = false): Flow<NetworkResult<List<Sub${NAME}>>>
    suspend fun observeSub${NAME}ById(uid: String): Flow<NetworkResult<Sub${NAME}>>
}