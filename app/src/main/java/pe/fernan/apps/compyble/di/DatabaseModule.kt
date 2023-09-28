package pe.fernan.apps.compyble.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pe.fernan.apps.compyble.data.local.db.CompyDb
import pe.fernan.apps.compyble.data.local.db.ProductFavoriteDao


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext app: Application): CompyDb =
        Room.databaseBuilder(app, CompyDb::class.java, "compy_db").fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideFavoriteDao(database: CompyDb) : ProductFavoriteDao= database.favoriteDao()


}