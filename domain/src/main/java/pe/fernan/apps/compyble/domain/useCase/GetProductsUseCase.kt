package pe.fernan.apps.compyble.domain.useCase

import kotlinx.coroutines.flow.Flow
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.repository.CompyRepository

class GetProductsUseCase(private val repository: CompyRepository) {
    operator fun invoke(category: String, subCategory: String, page: Int, sort: String) =
        repository.getProducts(category, subCategory, page, sort)

}
