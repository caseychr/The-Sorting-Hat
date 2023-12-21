package com.casey.thesortinghat.viewmodel

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.casey.thesortinghat.repository.CharacterRepository
import com.casey.thesortinghat.dto.CharacterDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun getAllCharacters() {
        viewModelScope.launch {
            repository.getAllCharacters().fold(
                ifLeft = { _uiState.value = UiState(CharacterState.Empty) },
                ifRight = { _uiState.value = UiState(CharacterState.Success(it)) }
            )
            //val characters = repository.getAllCharacters()
            //Log.i("CharacterViewModel", characters.toString())
        }
    }

    data class UiState(
        val state: CharacterState = CharacterState.Empty
    )
}

sealed class CharacterState {
    data class Success(val characters: List<CharacterDTO>): CharacterState()
    data class Error(val error: Error): CharacterState()
    object Empty: CharacterState()
}

inline fun <reified VM: ViewModel> ComponentActivity.viewModels(
    crossinline factoryProducer: () -> Provider<VM>
): Lazy<VM> = viewModels { ViewModelProviderFactory(factoryProducer()) }

class ViewModelProviderFactory<V: ViewModel>(private val provider: Provider<V>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return provider.get() as T
    }
}