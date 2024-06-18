package io.github.kabirnayeem99.mushafease.presentation.ui.directmushafpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.kabirnayeem99.mushafease.domain.entities.Error
import io.github.kabirnayeem99.mushafease.domain.entities.ErrorType
import io.github.kabirnayeem99.mushafease.domain.entities.Failed
import io.github.kabirnayeem99.mushafease.domain.entities.Loading
import io.github.kabirnayeem99.mushafease.domain.entities.Resource
import io.github.kabirnayeem99.mushafease.domain.entities.Success
import io.github.kabirnayeem99.mushafease.domain.repositories.MushafRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectMushafPageViewModel @Inject constructor(
    private val mushafRepository: MushafRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DirectMushafPageUiState())
    val uiState = _uiState.asStateFlow()

    private var loadMushafPageJob: Job? = null

    fun loadMushafPage(page: Int) {
        loadMushafPageJob?.cancel()
        loadMushafPageJob = viewModelScope.launch(Dispatchers.IO) {
            mushafRepository.ayahByPage(page).collect { res ->
                handleResource(res) { page ->
                    _uiState.update { us -> us.copy(page = page) }
                }
            }
        }
    }

    fun fetchSurahs() {
        viewModelScope.launch(Dispatchers.IO) {
            val surahs = uiState.value.surahs
            if (surahs.isNotEmpty()) return@launch
            mushafRepository.surahs().collect { res ->
                handleResource(res) { surahs ->
                    _uiState.update { us -> us.copy(surahs = surahs) }
                }
            }
        }
    }

    private suspend fun <T> handleResource(
        resource: Resource<T>,
        onDataFound: suspend (T) -> Unit,
    ) {
        when (resource) {
            is Error -> triggerErrorMessage(resource.message)
            is Failed -> triggerErrorMessage(resource.message)
            is Loading -> toggleLoading(true)
            is Success -> {
                toggleLoading(false)
                val data = resource.data
                if (data != null) onDataFound(data)
            }
        }
    }

    private fun triggerErrorMessage(errorType: ErrorType?) {

    }

    private fun toggleLoading(loading: Boolean) {
        _uiState.update { us -> us.copy(isLoading = loading) }
    }

}