package pe.fernan.apps.compyble.ui.screen.product

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import pe.fernan.apps.compyble.domain.useCase.GetSortKeysUseCase
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getSortKeysUseCase: GetSortKeysUseCase,
) : ViewModel() {
    var sortKeys: Flow<Map<String, String>> = emptyFlow()

    fun getSortKeys(category: String, subCategory: String) {
        sortKeys = getSortKeysUseCase.invoke(category, subCategory)
    }
}
