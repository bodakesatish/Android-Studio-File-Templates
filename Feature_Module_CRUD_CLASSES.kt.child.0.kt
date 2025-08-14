package ${PACKAGE_NAME}.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ${PACKAGE_NAME}.domain.model.${NAME}
import ${PACKAGE_NAME}.domain.repository.${NAME}Repository
import ${PACKAGE_NAME}.domain.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ${NAME}ListViewModel @Inject constructor(
    private val ${NAME.toLowerCase()}Repository: ${NAME}Repository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _${NAME.toLowerCase()}List = MutableStateFlow<List<${NAME}>>(emptyList())
    val ${NAME.toLowerCase()}List: StateFlow<List<${NAME}>> = _${NAME.toLowerCase()}List.asStateFlow()

    private val _snackBarMessage = MutableStateFlow<String?>(null)
    val snackBarMessage: StateFlow<String?> = _snackBarMessage.asStateFlow()

    private val _navigateToEdit${NAME}Event = MutableSharedFlow<String>() // Emits ${NAME.toLowerCase()}Id
    val navigateToEdit${NAME}Event: SharedFlow<String> =
        _navigateToEdit${NAME}Event.asSharedFlow()

    private val _navigateToAdd${NAME}Event = MutableSharedFlow<Unit>() // Emits Unit
    val navigateToAdd${NAME}Event: SharedFlow<Unit> = _navigateToAdd${NAME}Event.asSharedFlow()

    init {
        load${NAME}s(isForcedRefresh = false)
    }

    fun load${NAME}s(isForcedRefresh: Boolean = false) {
        viewModelScope.launch {
            ${NAME.toLowerCase()}Repository.get${NAME}s(isForcedRefresh = isForcedRefresh)
                .onStart {
                    _isLoading.value = true;
                    _snackBarMessage.value = null
                }
                .collect { result ->
                    _isLoading.value = false
                    when (result) {
                        is NetworkResult.Success -> {
                            _${NAME.toLowerCase()}List.value = result.data
                        }

                        is NetworkResult.Error -> {
                            _snackBarMessage.value = result.message
                            _${NAME.toLowerCase()}List.value = emptyList()
                        }

                        is NetworkResult.NoInternet -> {
                            _snackBarMessage.value = "No internet connection."
                            _${NAME.toLowerCase()}List.value = emptyList()
                        }

                        is NetworkResult.Loading -> {
                            _isLoading.value = true
                        }
                    }
                }
        }
    }

    fun onAdd${NAME}Clicked() {
        viewModelScope.launch {
            _navigateToAdd${NAME}Event.emit(Unit)
        }
    }

    fun on${NAME}Clicked(${NAME.toLowerCase()}: ${NAME}) {
        viewModelScope.launch {
            _navigateToEdit${NAME}Event.emit(${NAME.toLowerCase()}.uid)
        }
    }

    fun delete${NAME}(${NAME.toLowerCase()}: ${NAME}) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = ${NAME.toLowerCase()}Repository.delete${NAME}(${NAME.toLowerCase()}.uid)
            result.collect { networkResult ->
                _isLoading.value = false
                when (networkResult) {
                    is NetworkResult.Success -> {
                        _snackBarMessage.value = "${NAME} '${${NAME.toLowerCase()}.firstName}' deleted successfully."
                    }

                    is NetworkResult.Error -> {
                        _snackBarMessage.value = networkResult.message
                    }

                    is NetworkResult.NoInternet -> {
                        _snackBarMessage.value = "No internet to delete ${NAME.toLowerCase()}."
                    }

                    is NetworkResult.Loading -> _isLoading.value = true
                }
            }
        }
    }

    fun clearSnackBarMessage() {
        _snackBarMessage.value = null
    }
}