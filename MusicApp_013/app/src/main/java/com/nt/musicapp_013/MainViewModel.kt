package com.nt.musicapp_013

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class LibraryCategory(val name: String, @DrawableRes val icon: Int)
data class BottomSheetOption(val name: String, @DrawableRes val icon: Int)

class MainViewModel: ViewModel() {
    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.Account)
    val currentScreen: MutableState<Screen> get() = _currentScreen

    val categories = listOf("Hits", "Happy", "Workout", "Running", "TGIF", "Yoga")
    val grouped = listOf("New Release", "Favorite", "Top Rated", "Classics").groupBy { it[0] }
    val libraryCategories = listOf(
        LibraryCategory("Playlist", R.drawable.baseline_playlist_play_24),
        LibraryCategory("Artists", R.drawable.baseline_person_24),
        LibraryCategory("Album", R.drawable.baseline_album_24),
        LibraryCategory("Songs", R.drawable.baseline_queue_music_24),
        LibraryCategory("Genre", R.drawable.baseline_category_24),
    )
    val bottomSheetOptions = listOf(
        BottomSheetOption(name = "Settings", icon = R.drawable.baseline_settings_24),
        BottomSheetOption(name = "Help", icon = R.drawable.baseline_help_center_24),
        BottomSheetOption(name = "Share", icon = R.drawable.baseline_share_24)
    )

    fun randomizedCategories(): List<String> {
        return categories.shuffled() // Just to make the categories look distinct.
    }

    fun setCurrentScreen(screen: Screen) {
        _currentScreen.value = screen
    }

}