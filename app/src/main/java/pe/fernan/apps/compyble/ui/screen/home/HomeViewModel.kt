package pe.fernan.apps.compyble.ui.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pe.fernan.apps.compyble.domain.model.Data
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.model.Slider
import pe.fernan.apps.compyble.domain.useCase.DeleteProductLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.GetAllProductsFavoriteLocalUseCase
import pe.fernan.apps.compyble.domain.useCase.GetHomeDataUseCase
import pe.fernan.apps.compyble.domain.useCase.SaveProductLocalUseCase
import pe.fernan.apps.compyble.utils.Path
import pe.fernan.apps.compyble.utils.UrlUtils.findPathsInUrl
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeDataUseCase: GetHomeDataUseCase,
    private val getAllProductsFavoriteLocalUseCase: GetAllProductsFavoriteLocalUseCase,
    private val saveProductLocalUseCase: SaveProductLocalUseCase,
    private val deleteProductLocalUseCase: DeleteProductLocalUseCase
) : ViewModel() {

    val infoDialog =  mutableStateOf(true)


    val getData: MutableState<Data?> = mutableStateOf(null)

    private var isLoadingFirstFavorite = false


    var productCategories: SnapshotStateList<Pair<String, List<ProductState>>> =
        mutableStateListOf()

    fun evaluateFavorite(state: ProductState) {
        viewModelScope.launch {
            if (state.favorite) {
                deleteProductLocalUseCase.invoke(state.product)
            } else {
                saveProductLocalUseCase.invoke(state.product)
            }
        }
    }


    init {
        viewModelScope.launch {

            getHomeDataUseCase.invoke().collectLatest {
                getData.value = it
                val productCategories = getData.value?.productCategories?.map { data ->
                    data.first to data.second.map { product -> ProductState(product) }
                }
                this@HomeViewModel.productCategories.clear()
                this@HomeViewModel.productCategories.addAll(productCategories ?: listOf())
                evaluateFavorite()
            }


        }
    }


    private fun evaluateFavorite() {
        if (!isLoadingFirstFavorite) {
            isLoadingFirstFavorite = true
            viewModelScope.launch {
                getAllProductsFavoriteLocalUseCase().collect {
                    // Now Check
                    val mapProducts = it.associateBy { product -> product.href }
                    val productCategories = this@HomeViewModel.productCategories.toList()

                    val newProductCategories = productCategories.map { data ->
                        val label = data!!.first
                        val newStateProducts = data.second.map { state ->
                            val product = state.product
                            val checkProduct = mapProducts[product.href]
                            val favorite = checkProduct != null
                            ProductState(product, favorite)
                        }
                        label to newStateProducts
                    }
                    this@HomeViewModel.productCategories.clear()
                    this@HomeViewModel.productCategories.addAll(newProductCategories)

                }
            }

        }

    }

    fun setCloseInfoDialog() {
        infoDialog.value = false
    }

    // https://compy.pe/galeria?pagesize=24&page=1&sort=offer&category=Celulares&brand=APPLE
    // <a href="/galeria?pagesize=24&amp;page=1&amp;sort=offer&amp;category=Celulares&amp;brand=APPLE" class="btn btn-primary btn-rounded-more btn-primary-bluegray" banner-data="" banner-id="home-iphones" banner-name="iphones" banner-pos="Home - Banners Top - 1" banner-creative="/galeria?category=Celulares&amp;brand=APPLE" data-banner-ga4="" data-banner-id="home-iphones" data-banner-name="iphones" data-banner-pos="1" data-banner-format="Home categoría">Compara iPhones</a>
    fun processAndExtract(slide: Slider): List<Path> {
        val pathMap = findPathsInUrl(slide.href)
        return pathMap.map { Path(it.key, it.value) }
    }
    fun processAndExtract(href: String): List<Path> {
        val pathMap = findPathsInUrl(href)
        return pathMap.map { Path(it.key, it.value) }
    }

}

class ProductState(
    val product: Product,
    initialFavorite: Boolean = false
) {
    var favorite by mutableStateOf(initialFavorite)
}



fun main() {
    val urlString = "https://compy.pe/galeria?pagesize=24&page=1&sort=offer&category=Celulares&brand=APPLE"

    val queryParameters = findPathsInUrl(urlString)

    queryParameters.forEach { (key, value) ->
        println("$key: $value")
    }
}