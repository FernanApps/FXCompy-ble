package pe.fernan.apps.compyble.ui.screen.favorite

import android.app.appsearch.SearchResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.fernan.apps.compyble.domain.useCase.DeleteProductLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.GetAllProductsFavoriteLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.SaveProductLocalUseCase
import pe.fernan.apps.compyble.ui.screen.details.DetailsUiState
import pe.fernan.apps.compyble.ui.screen.home.ProductState
import javax.inject.Inject



@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllProductsFavoriteLocalUseCase: GetAllProductsFavoriteLocalUseCase,
    private val deleteProductLocalUseCase: DeleteProductLocalUseCase
) : ViewModel() {

    // Game UI state
    //private val _uiState = MutableStateFlow(FavoriteUiState())
    //val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    val uiStatea = getAllProductsFavoriteLocalUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList()).map {
        println("Chelkinss")
    }

    val products = getAllProductsFavoriteLocalUseCase.invoke().map { x ->
        println("_______xxxxxx")
        x.map { product -> ProductState(product, true) }
    }


    fun deleteFavorite(state: ProductState) {
        viewModelScope.launch {
            deleteProductLocalUseCase.invoke(state.product)
        }
    }



}