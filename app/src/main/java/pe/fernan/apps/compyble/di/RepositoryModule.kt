package pe.fernan.apps.compyble.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.fernan.apps.compyble.data.remote.CompyApi
import pe.fernan.apps.compyble.data.remote.home.HomeRepositoryImp
import pe.fernan.apps.compyble.domain.repository.HomeRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideHomeRepository(
        api: CompyApi
    ): HomeRepository = HomeRepositoryImp(api)
}