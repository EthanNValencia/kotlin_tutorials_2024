package com.nt.musicapp_013

import androidx.annotation.DrawableRes


enum class ScreenType {
    DRAWER,
    BOTTOM
}

sealed class Screen(val title: String, val route: String, @DrawableRes val icon: Int, val type: ScreenType) {
    data object Account: Screen(title = "Account", route = "account", icon = R.drawable.ic_account, type = ScreenType.DRAWER)
    data object Subscription: Screen(title = "Subscription", route = "subscribe", icon =  R.drawable.ic_subscribe, type = ScreenType.DRAWER)
    data object AddAccount: Screen(title = "Add Account", route = "add", icon = R.drawable.baseline_person_add_alt_1_24, type = ScreenType.DRAWER)
    data object Home: Screen(title = "Home", route = "home", icon = R.drawable.baseline_music_video_24, type = ScreenType.BOTTOM)
    data object Library: Screen(title = "Library", route = "library", icon = R.drawable.baseline_video_library_24, type = ScreenType.BOTTOM)
    data object Browse: Screen(title = "Browse", route = "browse", icon = R.drawable.baseline_apps_24, type = ScreenType.BOTTOM)
}

val drawerScreens = listOf(Screen.Account, Screen.Subscription, Screen.AddAccount)

val bottomScreens = listOf(Screen.Home, Screen.Library, Screen.Browse)