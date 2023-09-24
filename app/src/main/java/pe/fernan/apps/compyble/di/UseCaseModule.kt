package pe.fernan.apps.compyble.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.fernan.apps.compyble.domain.repository.HomeRepository
import pe.fernan.apps.compyble.domain.useCase.GetCategoriesUseCase
import pe.fernan.apps.compyble.domain.useCase.GetHomeDataUseCase
import pe.fernan.apps.compyble.domain.useCase.GetSortKeysUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetHomeDataUseCase(homeRepository: HomeRepository) = GetHomeDataUseCase(
        homeRepository
    )

    @Provides
    fun provideGetCategoriesUseCase(homeRepository: HomeRepository) = GetCategoriesUseCase(
        homeRepository
    )

    @Provides
    fun provideGetSortKeysUseCase(homeRepository: HomeRepository) = GetSortKeysUseCase(
        homeRepository
    )


}