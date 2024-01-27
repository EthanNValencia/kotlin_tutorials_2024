package com.nt.recipeapp_010

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null)

    private val _categoriesApiResponse = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoriesApiResponse

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories()
                _categoriesApiResponse.value = _categoriesApiResponse.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null)
            } catch (e: Exception) {
                _categoriesApiResponse.value = _categoriesApiResponse.value.copy(
                    loading = false,
                    error = "Error fetching categories ${e.message}"
                )
            }
        }
    }

}