package pe.fernan.apps.compyble.ui.screen.favorite

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.fernan.apps.compyble.domain.useCase.DeleteProductLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.GetAllProductsFavoriteLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.SaveProductLocalUseCase
import pe.fernan.apps.compyble.ui.screen.home.ProductState
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllProductsFavoriteLocalUseCase: GetAllProductsFavoriteLocalUseCase,
    private val saveProductLocalUseCase: SaveProductLocalUseCase,
    private val deleteProductLocalUseCase: DeleteProductLocalUseCase
) : ViewModel() {

    val products = mutableStateListOf<ProductState>()

    init {
        viewModelScope.launch {
            getAllProductsFavoriteLocalUseCase().collect {
                products.clear()
                products.addAll(it.map { product -> ProductState(product, true) })
            }

        }
    }

    fun evaluateFavorite(state: ProductState) {
        viewModelScope.launch {
            if (state.favorite) {
                deleteProductLocalUseCase.invoke(state.product)
            } else {
                saveProductLocalUseCase.invoke(state.product)
            }
        }
    }
}