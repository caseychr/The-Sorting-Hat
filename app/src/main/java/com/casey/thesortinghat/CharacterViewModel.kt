package com.casey.thesortinghat

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.casey.thesortinghat.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class CharacterViewModel @Inject constructor(
    private val repository: SortingHatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun getAllCharacters() {
        viewModelScope.launch {
            repository.getAllCharacters().fold(
                ifLeft = { _uiState.value = UiState(State.Empty) },
                ifRight = { _uiState.value = UiState(State.Success(it)) }
            )
            //val characters = repository.getAllCharacters()
            //Log.i("CharacterViewModel", characters.toString())
        }
    }

    data class UiState(
        val state: State = State.Empty
    )
}

sealed class State {
    data class Success(val characters: List<Character>): State()
    data class Error(val error: Error): State()
    object Empty: State()
}

inline fun <reified VM: ViewModel> ComponentActivity.viewModels(
    crossinline factoryProducer: () -> Provider<VM>
): Lazy<VM> = viewModels { ViewModelProviderFactory(factoryProducer()) }

class ViewModelProviderFactory<V: ViewModel>(private val provider: Provider<V>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return provider.get() as T
    }
}