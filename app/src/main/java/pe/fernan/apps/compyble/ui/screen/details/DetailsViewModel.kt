package pe.fernan.apps.compyble.ui.screen.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pe.fernan.apps.compyble.domain.model.Details
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.useCase.DeleteProductLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.GetDetailsUseCase
import pe.fernan.apps.compyble.domain.useCase.GetProductByIdUseCase
import pe.fernan.apps.compyble.domain.useCase.SaveProductLocalUseCase
import java.util.concurrent.Flow
import javax.inject.Inject


data class DetailsUiState(
    val favorite: Boolean = false
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsUseCase: GetDetailsUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val saveProductLocalUseCase: SaveProductLocalUseCase,
    private val deleteProductLocalUseCase: DeleteProductLocalUseCase
) : ViewModel() {

    // Game UI state
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()


    var _product: Product? = null
    var details = emptyFlow<Details>()


    fun getDetails(path: String) {
        viewModelScope.launch {
            details = detailsUseCase.invoke(path)
        }
    }

    fun removeFavorite() {
        viewModelScope.launch {
            deleteProductLocalUseCase.invoke(_product!!)
        }
    }

    fun addFavorite() {
        viewModelScope.launch {
            saveProductLocalUseCase.invoke(_product!!)
        }
    }

    fun setProduct(product: Product) {
        this._product = product
        viewModelScope.launch {
            getProductByIdUseCase(product.href).collect {
                _uiState.update { detailsUiState ->
                    detailsUiState.copy(favorite = it != null)
                }

            }

        }
    }

    fun addOrRemoveFavorite() {
        if(_uiState.value.favorite){
            // remove
            removeFavorite()
            _uiState.update { detailsUiState ->
                detailsUiState.copy(favorite = false)
            }
        } else {
            // add
            addFavorite()
            _uiState.update { detailsUiState ->
                detailsUiState.copy(favorite = true)
            }
        }

    }

}