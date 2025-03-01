package com.tg.android.fetchce.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tg.android.fetchce.data.remote.NetworkResult
import com.tg.android.fetchce.domain.model.GroupedItems
import com.tg.android.fetchce.domain.model.ItemSortOption
import com.tg.android.fetchce.domain.usecase.GetItemsUseCase
import com.tg.android.fetchce.domain.usecase.SortItemsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ItemViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val sortItemsUseCase: SortItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ItemsUiState>(ItemsUiState.Loading)
    val uiState: StateFlow<ItemsUiState> = _uiState.asStateFlow()

    private val _sortBy = MutableStateFlow<ItemSortOption>(ItemSortOption.ALPHABETICAL)
    val sortBy: StateFlow<ItemSortOption> = _sortBy.asStateFlow()

    private val _currListId = MutableStateFlow<Int>(0)
    val currListId: StateFlow<Int> = _currListId.asStateFlow()

    private var originalData: List<GroupedItems> = emptyList()

    init {
        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            // Note: onEach is not a performance issue
            // as only one list(irrespective of size) is emitted
            getItemsUseCase().onEach { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _uiState.value = ItemsUiState.Loading
                    }

                    is NetworkResult.Success -> {
                        originalData = result.data
                        if (originalData.isNotEmpty() && _currListId.value == 0) {
                            // Initialize with the first group ID
                            _currListId.value = originalData.first().listId
                        }
                        applyCurrentSorting()
                    }

                    is NetworkResult.Error -> {
                        _uiState.value = ItemsUiState.Error(result.message)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun setSortOption(option: ItemSortOption) {
        if (_sortBy.value != option) {
            _sortBy.value = option
            applyCurrentSorting()
        }
    }

    fun setCurrentGroupId(groupId: Int) {
        _currListId.value = groupId
    }

    private fun applyCurrentSorting() {
        if (originalData.isEmpty()) return

        sortItemsUseCase(originalData, _sortBy.value).onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    // Just keep the current state
                }

                is NetworkResult.Success -> {
                    _uiState.value = ItemsUiState.Success(result.data)
                }

                is NetworkResult.Error -> {
                    _uiState.value = ItemsUiState.Error(result.message)
                }
            }
        }.launchIn(viewModelScope)
    }
}

sealed class ItemsUiState {
    object Loading : ItemsUiState()
    data class Success(val data: List<GroupedItems>) : ItemsUiState()
    data class Error(val message: String) : ItemsUiState()
}