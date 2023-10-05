package pe.fernan.apps.compyble.domain.useCase

import pe.fernan.apps.compyble.domain.repository.CompyRepository

class GetSortKeysUseCase(private val homeRepository: CompyRepository) {
    operator fun invoke(paths: Map<String, String>) = homeRepository.getSortKeys(paths)
}




