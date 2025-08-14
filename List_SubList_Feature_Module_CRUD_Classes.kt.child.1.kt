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
class AddEditSub${NAME}ViewModel @Inject constructor(
    private val sub${NAME}Repository: Sub${NAME}Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val ${NAME.toLowerCase()}Id: String? = savedStateHandle.get<String>("${NAME.toLowerCase()}Id")

    private val sub${NAME}Id: String? = savedStateHandle.get<String>("sub${NAME}Id")
    val isEditing: Boolean = sub${NAME}Id != null

    private val _sub${NAME} = MutableStateFlow(Sub${NAME}())
    val sub${NAME}: StateFlow<Sub${NAME}> = _sub${NAME}.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _snackBarMessage = MutableStateFlow<String?>(null)
    val snackBarMessage: StateFlow<String?> = _snackBarMessage.asStateFlow()

    val isSaveButtonEnabled: StateFlow<Boolean> = _isLoading.map { !it }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    private val _navigateBackEvent = MutableSharedFlow<Unit>()
    val navigateBackEvent: SharedFlow<Unit> = _navigateBackEvent.asSharedFlow()

    init {
        if (sub${NAME}Id != null) {
            loadSub${NAME}Details(sub${NAME}Id)
        }
    }

    private fun loadSub${NAME}Details(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            sub${NAME}Repository.observeSub${NAME}ById(id).collect { result ->
                _isLoading.value = false
                when (result) {
                    is NetworkResult.Success -> {
                        _sub${NAME}.value = result.data
                    }
                    is NetworkResult.Error -> {
                        _snackBarMessage.value = result.message
                    }
                    is NetworkResult.NoInternet -> {
                        _snackBarMessage.value = "No internet to load details."
                    }
                    is NetworkResult.Loading -> _isLoading.value = true
                }
            }
        }
    }

    fun saveSub${NAME}(name: String, price: Double) {
        if (name.isEmpty()) {
            _snackBarMessage.value = "Please fill in name field."
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            val uid = sub${NAME}Id ?: ""
            val sub${NAME}ToSave = Sub${NAME}(
                uid = uid,
                ${NAME.toLowerCase()}Id = ${NAME.toLowerCase()}Id!!,
                name = name,
                price = price
            )
            val resultFlow = if (isEditing) {
                sub${NAME}Repository.updateSub${NAME}(sub${NAME}ToSave)
            } else {
                sub${NAME}Repository.createSub${NAME}(sub${NAME}ToSave)
            }

            resultFlow.collect { result ->
                _isLoading.value = false
                when (result) {
                    is NetworkResult.Loading -> {
                        _isLoading.value = true
                    }
                    is NetworkResult.Success -> {
                        _snackBarMessage.value = "Sub${NAME} saved successfully!" // Set success message
                        _navigateBackEvent.emit(Unit) // Emit navigation event
                    }
                    is NetworkResult.Error -> {
                        _snackBarMessage.value = result.message // Set success message
                    }
                    is NetworkResult.NoInternet -> {
                        _snackBarMessage.value = "No internet to save ${NAME.toLowerCase()}."
                    }
                }
            }
        }
    }

    fun clearSnackBarMessage() {
        _snackBarMessage.value = null
    }
}