package pe.fernan.apps.compyble.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.fernan.apps.compyble.domain.model.Category
import pe.fernan.apps.compyble.domain.model.Data
import pe.fernan.apps.compyble.domain.model.Details
import pe.fernan.apps.compyble.domain.model.Product

interface CompyRepository {
    fun getMain(): Flow<Data>
    fun getCategories(): Flow<List<Category>>

    fun getSortKeys(paths: Map<String, String>): Flow<List<Pair<String, String>>>
    fun getProducts(paths: Map<String, String>): Flow<List<Product>>
    fun getDetails(path: String): Flow<Details>



}