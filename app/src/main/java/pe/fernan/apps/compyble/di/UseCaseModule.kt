package pe.fernan.apps.compyble.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.repository.CompyRepository
import pe.fernan.apps.compyble.domain.repository.ProductRepository
import pe.fernan.apps.compyble.domain.useCase.DeleteProductLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.GetAllProductsFavoriteLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.GetCategoriesUseCase
import pe.fernan.apps.compyble.domain.useCase.GetDetailsUseCase
import pe.fernan.apps.compyble.domain.useCase.GetHomeDataUseCase
import pe.fernan.apps.compyble.domain.useCase.GetProductsRemoteUseCase
import pe.fernan.apps.compyble.domain.useCase.GetSortKeysUseCase
import pe.fernan.apps.compyble.domain.useCase.SaveProductLocalUseCase


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
    fun provideGetProductsUseCase(repository: CompyRepository) = GetProductsRemoteUseCase(
        repository
    )


    @Provides
    fun provideDetailsUseCase(repository: CompyRepository) = GetDetailsUseCase(
        repository
    )


    @Provides
    fun provideGetAllProductsFavoriteLocalUseCase(repository: ProductRepository): GetAllProductsFavoriteLocalUseCase {
        return GetAllProductsFavoriteLocalUseCase(repository)
    }

    @Provides
    fun provideSaveProductLocalUseCase(repository: ProductRepository): SaveProductLocalUseCase {
        return SaveProductLocalUseCase(repository)
    }

    @Provides
    fun provideDeleteProductLocalUseCase(repository: ProductRepository): DeleteProductLocalUseCase {
        return DeleteProductLocalUseCase(repository)
    }



}