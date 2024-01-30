package com.nt.mywishlist_013.database

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(private val wishRepository: WishRepository = Graph.wishRepository): ViewModel() {
    // private val _wishArray = mutableStateOf(DummyWishes.wishList)
    // val wishArray: State<List<Wish>> = _wishArray

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    lateinit var getAllWishes: Flow<List<Wish>>

    fun onWishTitleChange(newTitle: String) {
        wishTitleState = newTitle
    }
    fun onWishDescriptionChange(newDescription: String) {
        wishDescriptionState = newDescription
    }

    fun addWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.addAWish(wish = wish)
        }
    }

    fun updateWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateWish(wish = wish)
        }
    }

    fun deleteWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish = wish)
        }
    }

    fun getWishById(id: Long): Flow<Wish> {
        return wishRepository.getWishById(id = id)
    }

    fun getAllWishes(): Flow<List<Wish>> {
        return wishRepository.getAllWishes()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllWishes = getAllWishes()
        }
    }

}