package ${PACKAGE_NAME}.app

import androidx.lifecycle.SavedStateHandle
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
class Sub${NAME}ListViewModel @Inject constructor(
    private val sub${NAME}Repository: Sub${NAME}Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val ${NAME.toLowerCase()}Id: String = savedStateHandle.get<String>("${NAME.toLowerCase()}Id") as String
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _sub${NAME}List = MutableStateFlow<List<Sub${NAME}>>(emptyList())
    val sub${NAME}List: StateFlow<List<Sub${NAME}>> = _sub${NAME}List.asStateFlow()

    private val _snackBarMessage = MutableStateFlow<String?>(null)
    val snackBarMessage: StateFlow<String?> = _snackBarMessage.asStateFlow()

    private val _navigateToEditSub${NAME}Event = MutableSharedFlow<Pair<String, String>>() // Emits ${NAME.toLowerCase()}Id
    val navigateToEditSub${NAME}Event: SharedFlow<Pair<String, String>> =
        _navigateToEditSub${NAME}Event.asSharedFlow()

    private val _navigateToAddSub${NAME}Event = MutableSharedFlow<Pair<String, String?>>() // Emits Unit
    val navigateToAddSub${NAME}Event: SharedFlow<Pair<String, String?>> = _navigateToAddSub${NAME}Event.asSharedFlow()

    init {
        loadSub${NAME}s(isForcedRefresh = false)
    }

    fun loadSub${NAME}s(isForcedRefresh: Boolean = false) {
        viewModelScope.launch {
            sub${NAME}Repository.getSub${NAME}s(${NAME.toLowerCase()}Id, isForcedRefresh = isForcedRefresh)
                .onStart {
                    _isLoading.value = true;
                    _snackBarMessage.value = null
                }
                .collect { result ->
                    _isLoading.value = false
                    when (result) {
                        is NetworkResult.Success -> {
                            _sub${NAME}List.value = result.data
                        }

                        is NetworkResult.Error -> {
                            _snackBarMessage.value = result.message
                            _sub${NAME}List.value = emptyList()
                        }

                        is NetworkResult.NoInternet -> {
                            _snackBarMessage.value = "No internet connection."
                            _sub${NAME}List.value = emptyList()
                        }

                        is NetworkResult.Loading -> {
                            _isLoading.value = true
                        }
                    }
                }
        }
    }

    fun onAddSub${NAME}Clicked() {
        viewModelScope.launch {
            _navigateToAddSub${NAME}Event.emit(Pair(${NAME.toLowerCase()}Id, null))
        }
    }

    fun onSub${NAME}Clicked(sub${NAME}: Sub${NAME}) {
        viewModelScope.launch {
            _navigateToEditSub${NAME}Event.emit(Pair(${NAME.toLowerCase()}Id, sub${NAME}.uid))
        }
    }

    fun deleteSub${NAME}(sub${NAME}: Sub${NAME}) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = sub${NAME}Repository.deleteSub${NAME}(sub${NAME}.uid)
            result.collect { networkResult ->
                _isLoading.value = false
                when (networkResult) {
                    is NetworkResult.Success -> {
                        _snackBarMessage.value = "sub${NAME} sub${NAME}' deleted successfully."
                    }

                    is NetworkResult.Error -> {
                        _snackBarMessage.value = networkResult.message
                    }

                    is NetworkResult.NoInternet -> {
                        _snackBarMessage.value = "No internet to delete sub${NAME}."
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