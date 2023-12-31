package pe.fernan.apps.compyble.ui.screen.products

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.fernan.apps.compyble.domain.Constants
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.useCase.GetProductsRemoteUseCase
import pe.fernan.apps.compyble.domain.useCase.GetSortKeysUseCase
import pe.fernan.apps.compyble.utils.Path
import javax.inject.Inject



@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getSortKeysUseCase: GetSortKeysUseCase,
    private val getProductsUseCase: GetProductsRemoteUseCase
) : ViewModel() {

    private val isFirstLoading: MutableState<Boolean> = mutableStateOf(
        false)

    private var _paths: SnapshotStateMap<String, String> = mutableStateMapOf()


    private var _sortKeySelected: MutableState<Pair<String, String>?> = mutableStateOf(null)
    val sortKeySelected get() = _sortKeySelected


    private val _products = mutableStateListOf<Product>()
    val products get() = _products

    private val _page = mutableIntStateOf(1)
    val page get() = _page


    private val _sortKeySelectedLoading = mutableStateOf(false)
    val sortKeySelectedLoading get() = _sortKeySelectedLoading


    private val _sortKeys = mutableStateListOf<Pair<String, String>>()
    val sortKeys get() = _sortKeys

    val loading = mutableStateOf(false)

    fun setSortKeyLoading(value: Boolean) {
        _sortKeySelectedLoading.value = value
    }

    fun incrementPage() {
        _page.intValue++
    }

    fun getSortKeys() {
        viewModelScope.launch {
            getSortKeysUseCase(_paths.toMap()).collect {
                if (!isFirstLoading.value) {
                    isFirstLoading.value = true
                    _sortKeys.addAll(it)
                    setSortSelected(0)
                    getProducts()

                }


            }
        }
    }

    fun getProducts(
        deleteOldList: Boolean = false
    ) {
        val currentPage = _page.intValue
        println("getProducts in ViewModel Call ::: _page            ${_page.intValue}")
        println("getProducts in ViewModel Call ::: _sortKeySelected ${_sortKeySelected.value}")

        // https://compy.pe/galeria?
        // pagesize=24
        // &
        // page=1
        // &
        // sort=offer
        // &
        // category=Computadoras
        // &
        // subcategory=Consolas
        _paths[Constants.keyPage] = currentPage.toString()
        _paths[Constants.keySort] = _sortKeySelected.value!!.first

        viewModelScope.launch {
            getProductsUseCase(_paths).collect {
                if (deleteOldList) {
                    _products.clear()
                }
                _sortKeySelectedLoading.value = false
                loading.value = false
                _products.addAll(it)
                println("getProducts in ViewModel ::: ${products.toList()}")
            }

        }
        //products = getProductsUseCase(category, subCategory, page, sort)
    }

    fun setSortSelected(position: Int) {
        if (!_sortKeys.isEmpty()) {
            _sortKeySelected.value = _sortKeys.getOrNull(position)
        }
    }

    fun setSortSelected(item: Pair<String, String>) {
        if (!_sortKeys.isEmpty()) {
            _paths.put(Constants.keySort, item.first)
            _sortKeySelected.value = _sortKeys.find { it.first == item.first }
        }
    }

    fun checkIfEqualsSortSelected(itemKey: String): Boolean {
        return itemKey != sortKeySelected.value?.first
    }


    fun loadNextPage() {
        if (!loading.value) {
            viewModelScope.launch {
                loading.value = true
                page.intValue++
                getProducts()
            }
        }
    }

    fun setPaths(paths: List<Path>) {
        paths.forEach {
            _paths[it.key] = it.value
        }
    }
}
