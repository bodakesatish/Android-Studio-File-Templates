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
class AddEdit${NAME}ViewModel @Inject constructor(
    private val ${NAME.toLowerCase()}Repository: ${NAME}Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val ${NAME.toLowerCase()}Id: String? = savedStateHandle.get<String>("${NAME.toLowerCase()}Id")
    val isEditing: Boolean = ${NAME.toLowerCase()}Id != null

    private val _${NAME.toLowerCase()} = MutableStateFlow(${NAME}())
    val ${NAME.toLowerCase()}: StateFlow<${NAME}> = _${NAME.toLowerCase()}.asStateFlow()

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
        if (${NAME.toLowerCase()}Id != null) {
            load${NAME}Details(${NAME.toLowerCase()}Id)
        }
    }

    private fun load${NAME}Details(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            ${NAME.toLowerCase()}Repository.observe${NAME}ById(id).collect { result ->
                _isLoading.value = false
                when (result) {
                    is NetworkResult.Success -> {
                        _${NAME.toLowerCase()}.value = result.data
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

    fun save${NAME}(name: String, email: String, phone: String, address: String) {
        if (name.isEmpty()) {
            _snackBarMessage.value = "Please fill in name field."
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            val uid = ${NAME.toLowerCase()}Id ?: ""
            val ${NAME.toLowerCase()}ToSave = ${NAME}(
                uid = uid,
                firstName = name,
                email = email,
                phoneNumber = phone,
                address = address
            )
            val resultFlow = if (isEditing) {
                ${NAME.toLowerCase()}Repository.update${NAME}(${NAME.toLowerCase()}ToSave)
            } else {
                ${NAME.toLowerCase()}Repository.create${NAME}(${NAME.toLowerCase()}ToSave)
            }

            resultFlow.collect { result ->
                _isLoading.value = false
                when (result) {
                    is NetworkResult.Loading -> {
                        _isLoading.value = true
                    }
                    is NetworkResult.Success -> {
                        _snackBarMessage.value = "${NAME} saved successfully!" // Set success message
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