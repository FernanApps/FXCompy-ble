package pe.fernan.apps.compyble.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pe.fernan.apps.compyble.domain.useCase.DeleteProductLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.GetAllProductsFavoriteLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.SaveProductLocalUseCase
import pe.fernan.apps.compyble.ui.screen.home.ProductState
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllProductsFavoriteLocalUseCase: GetAllProductsFavoriteLocalUseCase,
    private val deleteProductLocalUseCase: DeleteProductLocalUseCase
) : ViewModel() {

    val products = getAllProductsFavoriteLocalUseCase.invoke().map { x ->
        println("_______xxxxxx")
        x.map { product -> ProductState(product, true) }
    }


    fun deleteFavorite(state: ProductState) {
        viewModelScope.launch {
            deleteProductLocalUseCase.invoke(state.product)
            products
        }
    }
}