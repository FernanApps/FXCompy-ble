package pe.fernan.apps.compyble.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CompyDb : RoomDatabase() {
    abstract fun favoriteDao(): ProductFavoriteDao
}





