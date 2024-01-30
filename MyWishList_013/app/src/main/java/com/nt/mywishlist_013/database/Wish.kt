package com.nt.mywishlist_013.database

import android.app.Application
import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// You do not need to a declare a function as suspend when it uses a coroutine like a Flow.
object DummyWishes {
    val wishList = listOf<Wish>(
        Wish(1, "Google Watch 1", "Android watch 1."),
        Wish(2, "Google Watch 2", "Android watch 2."),
        Wish(3, "Google Watch 4", "Android watch 4."),
        Wish(4, "Google Watch 12", "Android watch 12."),
        Wish(5, "Google Watch 32", "Android watch 32.")
    )
}
// Entity Begin
@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title: String,
    @ColumnInfo(name = "wish-description")
    val description: String
)
// Entity End
// DAO Begin
@Dao
abstract class WishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertWish(wishEntity: Wish)
    @Delete
    abstract suspend fun deleteWish(wishEntity: Wish)
    @Update
    abstract suspend fun updateWish(wishEntity: Wish)
    @Query("SELECT * FROM `wish-table` WHERE id=:id")
    abstract fun getWishById(id: Long): Flow<Wish>
    @Query("SELECT * FROM `wish-table`")
    abstract fun getAllWishes(): Flow<List<Wish>>
}
// DAO End
// Database Begin
@Database(
    entities = [Wish::class],
    version = 1,
    exportSchema = false
)
abstract class WishDatabase: RoomDatabase() {
    abstract fun wishDataAccessObject(): WishDao
}
// Database End
// Repository Begin
class WishRepository(private val wishDao: WishDao) {
    suspend fun addAWish(wish: Wish) {
        wishDao.insertWish(wish)
    }
    suspend fun deleteWish(wish: Wish) {
        wishDao.deleteWish(wish)
    }
    suspend fun updateWish(wish: Wish) {
        wishDao.updateWish(wish)
    }
    fun getAllWishes(): Flow<List<Wish>> = wishDao.getAllWishes()
    fun getWishById(id: Long) : Flow<Wish> = wishDao.getWishById(id)
}
// Repository End
// Graph Begin
object Graph { // object is used to declare a singleton in kotlin
    lateinit var database: WishDatabase

    val wishRepository by lazy { // This is lazy loaded, so it happens when the database is accessed.
        WishRepository(wishDao = database.wishDataAccessObject())
    }

    fun provide(context: Context) { // I wonder how much time this takes.
        database = Room.databaseBuilder(context = context, klass = WishDatabase::class.java, name = "wishlist.db").build()
    }

}
// Graph End
// WishListApp Begin
class WishListApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
// WishListApp End