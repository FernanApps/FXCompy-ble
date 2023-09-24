package pe.fernan.apps.compyble.ui.screen.product

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.useCase.GetProductsUseCase
import pe.fernan.apps.compyble.domain.useCase.GetSortKeysUseCase
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getSortKeysUseCase: GetSortKeysUseCase,
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val isFirstLoading: MutableState<Boolean> = mutableStateOf(false)

    private val _category = mutableStateOf("")
    private val _subCategory = mutableStateOf("")

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
            getSortKeysUseCase(_category.value, _subCategory.value).collect {
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
        println("getProducts in ViewModel Call ::: _category        ${_category.value}")
        println("getProducts in ViewModel Call ::: _subCategory     ${_subCategory.value}")
        println("getProducts in ViewModel Call ::: _page            ${_page.intValue}")
        println("getProducts in ViewModel Call ::: _sortKeySelected ${_sortKeySelected.value}")

        viewModelScope.launch {
            getProductsUseCase(
                _category.value,
                _subCategory.value,
                currentPage,
                _sortKeySelected.value!!.first
            ).collect {
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

    fun setInitial(category: String, subcategory: String) {
        _category.value = category
        _subCategory.value = subcategory
    }

    fun setSortSelected(position: Int) {
        if (!_sortKeys.isEmpty()) {
            _sortKeySelected.value = _sortKeys.getOrNull(position)
        }
    }

    fun setSortSelected(item: Pair<String, String>) {
        if (!_sortKeys.isEmpty()) {
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
}
