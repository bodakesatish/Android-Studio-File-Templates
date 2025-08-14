package ${PACKAGE_NAME}.data.repository

import ${PACKAGE_NAME}.domain.repository.${NAME}Repository
import ${PACKAGE_NAME}.data.source.local.${NAME}LocalDataSource
import ${PACKAGE_NAME}.data.source.remote.${NAME}RemoteDataSource
import ${PACKAGE_NAME}.domain.utils.NetworkResult
import ${PACKAGE_NAME}.domain.model.${NAME}
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ${NAME}RepositoryImpl @Inject constructor(
    private val localDataSource: ${NAME}LocalDataSource,
    private val remoteDataSource: ${NAME}RemoteDataSource
) : ${NAME}Repository {

    override suspend fun create${NAME}(${NAME.toLowerCase()}: ${NAME}): Flow<NetworkResult<${NAME}>> = flow {
        remoteDataSource.createRemote${NAME}(${NAME.toLowerCase()}).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    val user = networkResult.data
                    localDataSource.createOrUpdateLocal${NAME}(networkResult.data)
                    emit(NetworkResult.Success(user))
                }

                else -> {// if (networkResult is NetworkResult.Error || networkResult is NetworkResult.NoInternet)
                    emit(networkResult)
                }
            }
        }
    }

    override suspend fun update${NAME}(${NAME.toLowerCase()}: ${NAME}): Flow<NetworkResult<${NAME}>> = flow {
        remoteDataSource.updateRemote${NAME}(${NAME.toLowerCase()}).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    val user = networkResult.data
                    localDataSource.createOrUpdateLocal${NAME}(networkResult.data)
                    emit(NetworkResult.Success(user))
                }

                else -> {// if (networkResult is NetworkResult.Error || networkResult is NetworkResult.NoInternet)
                    emit(networkResult)
                }
            }
        }
    }

    override suspend fun delete${NAME}(uid: String): Flow<NetworkResult<Boolean>> = flow {
        remoteDataSource.deleteRemote${NAME}(uid).collect { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    localDataSource.deleteLocal${NAME}(uid)
                    emit(NetworkResult.Success(true))
                }

                else -> {// if (networkResult is NetworkResult.Error || networkResult is NetworkResult.NoInternet)
                    emit(networkResult)
                }
            }
        }
    }

    override suspend fun get${NAME}s(isForcedRefresh: Boolean): Flow<NetworkResult<List<${NAME}>>> =
        flow {
            emit(NetworkResult.Loading)
            var isRemoteCallSuccess = true
            if (isForcedRefresh || localDataSource.isLocalCacheEmpty()) {
                remoteDataSource.getRemote${NAME}s().collect { networkResult ->
                    when (networkResult) {
                        is NetworkResult.Success -> {
                            val ${NAME.toLowerCase()}List = networkResult.data
                            localDataSource.deleteAllLocal${NAME}()
                            localDataSource.saveLocal${NAME}List(${NAME.toLowerCase()}List)
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
                localDataSource.observeLocal${NAME}s().collect { ${NAME.toLowerCase()}List ->
                    emit(NetworkResult.Success(${NAME.toLowerCase()}List))
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun observe${NAME}ById(uid: String): Flow<NetworkResult<${NAME}>> {
        val ${NAME.toLowerCase()} = localDataSource.observeLocal${NAME}ById(uid).map {
            if (it != null) {
                NetworkResult.Success(it)
            } else {
                NetworkResult.Error("${NAME} not found")
            }
        }
        return ${NAME.toLowerCase()}
    }

}