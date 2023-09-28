package pe.fernan.apps.compyble.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pe.fernan.apps.compyble.data.Constants.tableProduct

@Dao
interface ProductFavoriteDao {

    @Query("SELECT * FROM $tableProduct")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(product: List<ProductEntity>)

    @Query("DELETE FROM $tableProduct")
    suspend fun deleteAllProducts()

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM $tableProduct WHERE href = :href")
    fun getProductById(href: String): Flow<ProductEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProduct(product: ProductEntity)
}