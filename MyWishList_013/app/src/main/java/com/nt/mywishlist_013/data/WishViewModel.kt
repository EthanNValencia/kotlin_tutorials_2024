package com.nt.mywishlist_013.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WishViewModel: ViewModel() {
    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChange(newTitle: String) {
        wishTitleState = newTitle
    }
    fun onWishDescriptionChange(newDescription: String) {
        wishDescriptionState = newDescription
    }
}