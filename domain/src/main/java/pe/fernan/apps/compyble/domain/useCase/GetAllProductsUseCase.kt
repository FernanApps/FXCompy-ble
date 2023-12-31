package pe.fernan.apps.compyble.domain.useCase

import kotlinx.coroutines.flow.Flow
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.repository.ProductRepository

class GetAllProductsFavoriteLocalUseCase(private val repository: ProductRepository) {
    operator fun invoke() = repository.getAllProducts()

}

class SaveProductLocalUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(product: Product) =
        repository.saveProduct(product)
}

class DeleteProductLocalUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(product: Product) =
        repository.deleteProduct(product)
}


class GetProductByIdUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(href: String) =
        repository.getProductById(href)
}