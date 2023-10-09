package pe.fernan.apps.compyble.data.local

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pe.fernan.apps.compyble.data.local.db.ProductEntity
import pe.fernan.apps.compyble.data.local.db.ProductFavoriteDao
import pe.fernan.apps.compyble.data.local.db.toDomain
import pe.fernan.apps.compyble.data.local.db.toEntity
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productFavoriteDao: ProductFavoriteDao) :
    ProductRepository {

    override fun getAllProducts(): Flow<List<Product>> =
        productFavoriteDao.getAllProducts().map { productsEntities ->
            println("Update getAllProducts")
            productsEntities.map { it.toDomain() }
        }

    override suspend fun insertAllProducts(product: List<Product>) {
        productFavoriteDao.insertAllProducts(product.map { it.toEntity() })
    }

    override suspend fun deleteAllProducts() {
        productFavoriteDao.deleteAllProducts()
    }

    override suspend fun deleteProduct(product: Product) {
        println("ProductRepositoryImpl deleteProduct $product")
        productFavoriteDao.deleteProduct(product.toEntity())
    }

    override fun getProductById(href: String): Flow<Product?> {
        return productFavoriteDao.getProductById(href).map { it?.toDomain() }
    }

    override suspend fun saveProduct(product: Product) {
        println("ProductRepositoryImpl saveProduct $product")

        productFavoriteDao.saveProduct(product.toEntity())
    }
}

