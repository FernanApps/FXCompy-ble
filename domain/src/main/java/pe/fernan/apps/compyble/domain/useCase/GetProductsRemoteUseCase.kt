package pe.fernan.apps.compyble.domain.useCase

import pe.fernan.apps.compyble.domain.repository.CompyRepository

class GetProductsRemoteUseCase(private val repository: CompyRepository) {
    operator fun invoke(paths: Map<String, String>) =
        repository.getProducts(paths)

}
