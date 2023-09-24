package pe.fernan.apps.compyble.domain.useCase

import pe.fernan.apps.compyble.domain.repository.HomeRepository

class GetSortKeysUseCase(private val homeRepository: HomeRepository) {
    operator fun invoke(
        category: String,
        subCategory: String
    ) = homeRepository.getSortKeys(
        category,
        subCategory
    )
}