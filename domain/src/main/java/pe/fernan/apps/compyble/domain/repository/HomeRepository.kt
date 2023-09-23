package pe.fernan.apps.compyble.domain.repository

import kotlinx.coroutines.flow.Flow
import pe.fernan.apps.compyble.domain.model.Data

interface HomeRepository {

    fun getMain(): Flow<Data>

}