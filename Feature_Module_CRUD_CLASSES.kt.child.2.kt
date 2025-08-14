package ${PACKAGE_NAME}.data.source.remote

import android.util.Log
import ${PACKAGE_NAME}.domain.utils.NetworkResult
import ${PACKAGE_NAME}.data.source.remote.utils.NetworkConnectivityService
import ${PACKAGE_NAME}.domain.model.${NAME}
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ${NAME}RemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val networkConnectivityService: NetworkConnectivityService
) : ${NAME}RemoteDataSource {

    val tag: String = javaClass::class.java.name

    val collection = "${NAME.toLowerCase()}s"

    override suspend fun createRemote${NAME}(${NAME.toLowerCase()}: ${NAME}): Flow<NetworkResult<${NAME}>> =
        flow {
            emit(NetworkResult.Loading)
            if (!networkConnectivityService.isNetworkAvailable()) {
                emit(NetworkResult.NoInternet)
                return@flow
            }
            try {
                // Let Firestore generate the document ID
                val documentReference = firestore.collection(collection).document() // Call document() without an ID
                // Create a copy of the ${NAME.toLowerCase()} with the new Firestore-generated ID
                val ${NAME.toLowerCase()}WithFirestoreId = ${NAME.toLowerCase()}.copy(uid = documentReference.id)
                documentReference.set(${NAME.toLowerCase()}WithFirestoreId.toHashMap()).await()
                emit(NetworkResult.Success(${NAME.toLowerCase()}WithFirestoreId))
            } catch (e: FirebaseFirestoreException) {
                Log.e(tag, "Firestore error saving ${NAME.toLowerCase()}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message ?: "Failed to save ${NAME.toLowerCase()} data to Firestore.",
                        code = e.code.value(),
                        exception = e
                    )
                )
            } catch (e: Exception) {
                Log.e(tag, "Error saving ${NAME.toLowerCase()}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message ?: "An unexpected error occurred while saving ${NAME.toLowerCase()} data.",
                        code = 0,
                        exception = e
                    )
                )
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun updateRemote${NAME}(${NAME.toLowerCase()}: ${NAME}): Flow<NetworkResult<${NAME}>> =
        flow {
            emit(NetworkResult.Loading)
            if (!networkConnectivityService.isNetworkAvailable()) {
                emit(NetworkResult.NoInternet)
                return@flow
            }
            try {
                // Let Firestore generate the document ID
                val documentReference = firestore.collection(collection)
                    .document(${NAME.toLowerCase()}.uid) // Call document() without an ID
                documentReference.set(${NAME.toLowerCase()}.toHashMap()).await()
                emit(NetworkResult.Success(${NAME.toLowerCase()}))
            } catch (e: FirebaseFirestoreException) {
                Log.e(tag, "Firestore error saving ${NAME.toLowerCase()}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message ?: "Failed to update ${NAME.toLowerCase()} data to Firestore.",
                        code = e.code.value(),
                        exception = e
                    )
                )
            } catch (e: Exception) {
                Log.e(tag, "Error updating ${NAME.toLowerCase()}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message ?: "An unexpected error occurred while updating ${NAME.toLowerCase()} data.",
                        code = 0,
                        exception = e
                    )
                )
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun deleteRemote${NAME}(uid: String): Flow<NetworkResult<Boolean>> =
        flow {
            emit(NetworkResult.Loading)
            if (!networkConnectivityService.isNetworkAvailable()) {
                emit(NetworkResult.NoInternet)
                return@flow
            }
            try {
                val documentReference = firestore.collection(collection).document(uid)
                documentReference.delete().await()
                emit(NetworkResult.Success(true))
            } catch (e: FirebaseFirestoreException) {
                Log.e(tag, "Firestore error deleting ${NAME.toLowerCase()}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message ?: "Failed to delete ${NAME.toLowerCase()} data from Firestore.",
                        code = e.code.value(),
                        exception = e
                    )
                )
            } catch (e: Exception) {
                Log.e(tag, "Error deleting ${NAME.toLowerCase()}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message ?: "An unexpected error occurred while deleting ${NAME.toLowerCase()} data.",
                        code = 0,
                        exception = e
                    )
                )
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getRemote${NAME}s(): Flow<NetworkResult<List<${NAME}>>> = flow {
        emit(NetworkResult.Loading)
        if (!networkConnectivityService.isNetworkAvailable()) {
            emit(NetworkResult.NoInternet)
            return@flow
        }
        try {
            val querySnapshot = firestore.collection(collection).get().await()
            val ${NAME.toLowerCase()}List = querySnapshot.documents.mapNotNull { document -> document.toObject(${NAME}::class.java) }
            emit(NetworkResult.Success(${NAME.toLowerCase()}List))
        } catch (e: FirebaseFirestoreException) {
            Log.e(tag, "Firestore error fetching ${NAME.toLowerCase()} list: ${e.message}", e)
            emit(
                NetworkResult.Error(
                    e.message ?: "Failed to fetch ${NAME.toLowerCase()} list from Firestore.",
                    code = e.code.value(),
                    exception = e
                )
            )
        } catch (e: Exception) {
            Log.e(tag, "Error fetching ${NAME.toLowerCase()} list: ${e.message}", e)
            emit(
                NetworkResult.Error(
                    e.message ?: "An unexpected error occurred while fetching ${NAME.toLowerCase()} list.",
                    code = 0,
                    exception = e
                )
            )
        }
    }.flowOn(Dispatchers.IO)

}