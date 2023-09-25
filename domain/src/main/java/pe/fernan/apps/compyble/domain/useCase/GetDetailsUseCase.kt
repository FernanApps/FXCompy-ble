package pe.fernan.apps.compyble.domain.useCase

import pe.fernan.apps.compyble.domain.repository.CompyRepository

class GetDetailsUseCase(private val homeRepository: CompyRepository) {
    operator fun invoke(
        path: String
    ) = homeRepository.getDetails(path)
}