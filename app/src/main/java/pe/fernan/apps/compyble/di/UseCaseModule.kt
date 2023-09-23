package pe.fernan.apps.compyble.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.fernan.apps.compyble.domain.repository.HomeRepository
import pe.fernan.apps.compyble.domain.useCase.GetHomeDataUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetHomeDataUseCase(homeRepository: HomeRepository) = GetHomeDataUseCase(
        homeRepository
    )

}