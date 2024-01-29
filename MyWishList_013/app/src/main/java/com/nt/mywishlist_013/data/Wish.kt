package com.nt.mywishlist_013.data

data class Wish(
    val id: Long = 0L,
    val title: String,
    val description: String
)

object DummyWishes {
    val wishList = listOf<Wish>(
        Wish(1, "Google Watch 1", "Android watch 1."),
        Wish(1, "Google Watch 2", "Android watch 2."),
        Wish(1, "Google Watch 4", "Android watch 4."),
        Wish(1, "Google Watch 12", "Android watch 12."),
        Wish(1, "Google Watch 32", "Android watch 32.")
    )
}