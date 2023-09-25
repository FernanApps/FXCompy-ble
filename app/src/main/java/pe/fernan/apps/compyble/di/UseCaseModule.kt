package pe.fernan.apps.compyble.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pe.fernan.apps.compyble.domain.repository.CompyRepository
import pe.fernan.apps.compyble.domain.useCase.GetCategoriesUseCase
import pe.fernan.apps.compyble.domain.useCase.GetDetailsUseCase
import pe.fernan.apps.compyble.domain.useCase.GetHomeDataUseCase
import pe.fernan.apps.compyble.domain.useCase.GetProductsUseCase
import pe.fernan.apps.compyble.domain.useCase.GetSortKeysUseCase


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetHomeDataUseCase(repository: CompyRepository) = GetHomeDataUseCase(
        repository
    )

    @Provides
    fun provideGetCategoriesUseCase(repository: CompyRepository) = GetCategoriesUseCase(
        repository
    )

    @Provides
    fun provideGetSortKeysUseCase(repository: CompyRepository) = GetSortKeysUseCase(
        repository
    )

    @Provides
    fun provideGetProductsUseCase(repository: CompyRepository) = GetProductsUseCase(
        repository
    )


    @Provides
    fun provideDetailsUseCase(repository: CompyRepository) = GetDetailsUseCase(
        repository
    )

}