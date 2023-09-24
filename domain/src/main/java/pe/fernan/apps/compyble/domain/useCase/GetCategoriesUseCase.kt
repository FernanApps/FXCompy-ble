package pe.fernan.apps.compyble.domain.useCase

import pe.fernan.apps.compyble.domain.repository.HomeRepository

class GetCategoriesUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke() = homeRepository.getCategories()
}

