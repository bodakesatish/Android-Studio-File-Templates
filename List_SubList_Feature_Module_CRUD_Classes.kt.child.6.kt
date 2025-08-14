package ${PACKAGE_NAME}.data.repository

import ${PACKAGE_NAME}.domain.repository.Sub${NAME}Repository
import ${PACKAGE_NAME}.data.source.local.Sub${NAME}LocalDataSource
import ${PACKAGE_NAME}.data.source.remote.Sub${NAME}RemoteDataSource
import ${PACKAGE_NAME}.domain.utils.NetworkResult
import ${PACKAGE_NAME}.domain.model.Sub${NAME}
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Sub${NAME}RepositoryImpl @Inject constructor(
    private val localDataSource: Sub${NAME}LocalDataSource,
    private val remoteDataSource: Sub${NAME}RemoteDataSource
) : Sub${NAME}Repository {

    override suspend fun createSub${NAME}(sub${NAME}: Sub${NAME}): Flow<NetworkResult<Sub${NAME}>> =
        flow {
            remoteDataSource.createRemoteSub${NAME}(sub${NAME}).collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        val user = networkResult.data
                        localDataSource.createOrUpdateLocalSub${NAME}(networkResult.data)
                        emit(NetworkResult.Success(user))
                    }

                    else -> {// if (networkResult is NetworkResult.Error || networkResult is NetworkResult.NoInternet)
                        emit(networkResult)
                    }
                }
            }
        }

    override suspend fun updateSub${NAME}(sub${NAME}: Sub${NAME}): Flow<NetworkResult<Sub${NAME}>> =
        flow {
            remoteDataSource.updateRemoteSub${NAME}(sub${NAME}).collect { networkResult ->
                when (networkResult) {
                    is NetworkResult.Success -> {
                        val user = networkResult.data
                        localDataSource.createOrUpdateLocalSub${NAME}(networkResult.data)
                        emit(NetworkResult.Success(user))
                    }

                    else -> {// if (networkResult is NetworkResult.Error || networkResult is NetworkResult.NoInternet)
                        emit(networkResult)
                    }
                }
            }
        }

    override suspend fun deleteSub${NAME}(uid: String): Flow<NetworkResult<Boolean>> = flow {
        remoteDataSource.deleteRemoteSub${NAME}(uid).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    localDataSource.deleteLocalSub${NAME}(uid)
                    emit(NetworkResult.Success(true))
                }

                else -> {// if (networkResult is NetworkResult.Error || networkResult is NetworkResult.NoInternet)
                    emit(networkResult)
                }
            }
        }
    }

    override suspend fun getSub${NAME}s(categoryId: String, isForcedRefresh: Boolean): Flow<NetworkResult<List<Sub${NAME}>>> =
        flow {
            emit(NetworkResult.Loading)
            var isRemoteCallSuccess = true
            if (isForcedRefresh || localDataSource.isLocalCacheEmpty(categoryId)) {
                remoteDataSource.getRemoteSub${NAME}s(categoryId).collect { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Success -> {
                            val sub${NAME}List = networkResult.data
                            localDataSource.deleteAllLocalSub${NAME}(categoryId)
                            localDataSource.saveLocalSub${NAME}List(sub${NAME}List)
                            isRemoteCallSuccess = true
                        }

                        else -> {// if (networkResult is NetworkResult.Error || networkResult is NetworkResult.NoInternet)
                            emit(networkResult)
                            isRemoteCallSuccess = false
                        }
                    }
                }
            }
            if (isRemoteCallSuccess) {
                localDataSource.observeLocalSub${NAME}s(categoryId).collect { sub${NAME}List ->
                    emit(NetworkResult.Success(sub${NAME}List))
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun observeSub${NAME}ById(uid: String): Flow<NetworkResult<Sub${NAME}>> {
        val sub${NAME} = localDataSource.observeLocalSub${NAME}ById(uid).map {
            if (it != null) {
                NetworkResult.Success(it)
            } else {
                NetworkResult.Error("Sub${NAME} not found")
            }
        }
        return sub${NAME}
    }

}