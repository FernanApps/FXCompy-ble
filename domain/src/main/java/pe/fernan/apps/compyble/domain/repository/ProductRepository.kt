package pe.fernan.apps.compyble.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.fernan.apps.compyble.domain.model.Product

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    suspend fun insertAllProducts(product: List<Product>)
    suspend fun deleteAllProducts()
    suspend fun deleteProduct(product: Product)
    fun getProductById(href: String): Flow<Product?>
    suspend fun saveProduct(product: Product)
}