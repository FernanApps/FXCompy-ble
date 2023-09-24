package pe.fernan.apps.compyble.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.fernan.apps.compyble.data.remote.CompyApi
import pe.fernan.apps.compyble.data.remote.CompyRepositoryImp
import pe.fernan.apps.compyble.domain.repository.CompyRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCompyRepository(
        api: CompyApi
    ): CompyRepository = CompyRepositoryImp(api)
}