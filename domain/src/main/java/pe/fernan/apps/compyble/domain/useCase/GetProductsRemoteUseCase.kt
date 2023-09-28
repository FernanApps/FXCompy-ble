package pe.fernan.apps.compyble.domain.useCase

import pe.fernan.apps.compyble.domain.repository.CompyRepository

class GetProductsRemoteUseCase(private val repository: CompyRepository) {
    operator fun invoke(category: String, subCategory: String, page: Int, sort: String) =
        repository.getProducts(category, subCategory, page, sort)

}
