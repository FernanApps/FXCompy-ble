package pe.fernan.apps.compyble.domain.useCase

import pe.fernan.apps.compyble.domain.repository.CompyRepository

class GetCategoriesUseCase(private val homeRepository: CompyRepository) {
    operator fun invoke() = homeRepository.getCategories()
}

