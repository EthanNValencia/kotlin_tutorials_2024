package com.nt.mywishlist_013.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State

class WishViewModel: ViewModel() {
    private val _wishArray = mutableStateOf(DummyWishes.wishList)
    val wishArray: State<List<Wish>> = _wishArray

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChange(newTitle: String) {
        wishTitleState = newTitle
    }
    fun onWishDescriptionChange(newDescription: String) {
        wishDescriptionState = newDescription
    }

    fun addWish(wish: Wish) {
        // Get the current value of _wishArray
        val currentWishes = _wishArray.value.toMutableList()
        // Add the new wish to the list
        currentWishes.add(wish)
        // Update the value of _wishArray with the new list
        _wishArray.value = currentWishes
    }

    fun saveEditWish(wish: Wish) {
        val currentWishes = _wishArray.value.toMutableList()
        val editWish = currentWishes.filter { wish.id == it.id }
    }

}