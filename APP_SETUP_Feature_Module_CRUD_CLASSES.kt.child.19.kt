package ${PACKAGE_NAME}.domain.utils

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(
        val message: String,
        val code: Int? = null, // Added: Optional error code
        val exception: Throwable? = null
    ) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>() // Represents the loading state
    object NoInternet : NetworkResult<Nothing>() // Specific state for no internet
}