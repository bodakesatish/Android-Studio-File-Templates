package ${PACKAGE_NAME}.data.source.remote

import android.util.Log
import ${PACKAGE_NAME}.domain.utils.NetworkResult
import ${PACKAGE_NAME}.data.source.remote.utils.NetworkConnectivityService
import ${PACKAGE_NAME}.domain.model.Sub${NAME}
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
class Sub${NAME}RemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val networkConnectivityService: NetworkConnectivityService
) : Sub${NAME}RemoteDataSource {

    val tag: String = javaClass::class.java.name

    val collection = "sub${NAME.toLowerCase()}"

    override suspend fun createRemoteSub${NAME}(sub${NAME}: Sub${NAME}): Flow<NetworkResult<Sub${NAME}>> =
        flow {
            emit(NetworkResult.Loading)
            if (!networkConnectivityService.isNetworkAvailable()) {
                emit(NetworkResult.NoInternet)
                return@flow
            }
            try {
                // Let Firestore generate the document ID
                val documentReference =
                    firestore.collection(collection).document() // Call document() without an ID
                // Create a copy of the ${NAME.toLowerCase()} with the new Firestore-generated ID
                val sub${NAME}WithFirestoreId = sub${NAME}.copy(uid = documentReference.id)
                documentReference.set(sub${NAME}WithFirestoreId.toHashMap()).await()
                emit(NetworkResult.Success(sub${NAME}WithFirestoreId))
            } catch (e: FirebaseFirestoreException) {
                Log.e(tag, "Firestore error saving Sub${NAME}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message ?: "Failed to save Sub${NAME} data to Firestore.",
                        code = e.code.value(),
                        exception = e
                    )
                )
            } catch (e: Exception) {
                Log.e(tag, "Error saving Sub${NAME}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message ?: "An unexpected error occurred while saving Sub${NAME} data.",
                        code = 0,
                        exception = e
                    )
                )
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun updateRemoteSub${NAME}(sub${NAME}: Sub${NAME}): Flow<NetworkResult<Sub${NAME}>> =
        flow {
            emit(NetworkResult.Loading)
            if (!networkConnectivityService.isNetworkAvailable()) {
                emit(NetworkResult.NoInternet)
                return@flow
            }
            try {
                // Let Firestore generate the document ID
                val documentReference = firestore.collection(collection)
                    .document(sub${NAME}.uid) // Call document() without an ID
                documentReference.set(sub${NAME}.toHashMap()).await()
                emit(NetworkResult.Success(sub${NAME}))
            } catch (e: FirebaseFirestoreException) {
                Log.e(tag, "Firestore error saving Sub${NAME}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message ?: "Failed to update Sub${NAME} data to Firestore.",
                        code = e.code.value(),
                        exception = e
                    )
                )
            } catch (e: Exception) {
                Log.e(tag, "Error updating Sub${NAME}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message
                            ?: "An unexpected error occurred while updating Sub${NAME} data.",
                        code = 0,
                        exception = e
                    )
                )
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun deleteRemoteSub${NAME}(uid: String): Flow<NetworkResult<Boolean>> =
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
                Log.e(tag, "Firestore error deleting Sub${NAME}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message ?: "Failed to delete Sub${NAME} data from Firestore.",
                        code = e.code.value(),
                        exception = e
                    )
                )
            } catch (e: Exception) {
                Log.e(tag, "Error deleting Sub${NAME}: ${e.message}", e)
                emit(
                    NetworkResult.Error(
                        e.message
                            ?: "An unexpected error occurred while deleting Sub${NAME} data.",
                        code = 0,
                        exception = e
                    )
                )
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getRemoteSub${NAME}s(${NAME.toLowerCase()}Id: String): Flow<NetworkResult<List<Sub${NAME}>>> = flow {
        emit(NetworkResult.Loading)
        if (!networkConnectivityService.isNetworkAvailable()) {
            emit(NetworkResult.NoInternet)
            return@flow
        }
        try {
            val querySnapshot = firestore.collection(collection)
                .whereEqualTo("${NAME.toLowerCase()}Id", ${NAME.toLowerCase()}Id) // <-- ADD THIS LINE
                .get()
                .await()
            val sub${NAME}List =
                querySnapshot.documents.mapNotNull { document -> document.toObject(Sub${NAME}::class.java) }
            emit(NetworkResult.Success(sub${NAME}List))
        } catch (e: FirebaseFirestoreException) {
            Log.e(tag, "Firestore error fetching sub${NAME} list: ${e.message}", e)
            emit(
                NetworkResult.Error(
                    e.message ?: "Failed to fetch sub${NAME} list from Firestore.",
                    code = e.code.value(),
                    exception = e
                )
            )
        } catch (e: Exception) {
            Log.e(tag, "Error fetching sub${NAME} list: ${e.message}", e)
            emit(
                NetworkResult.Error(
                    e.message ?: "An unexpected error occurred while fetching sub${NAME} list.",
                    code = 0,
                    exception = e
                )
            )
        }
    }.flowOn(Dispatchers.IO)

}