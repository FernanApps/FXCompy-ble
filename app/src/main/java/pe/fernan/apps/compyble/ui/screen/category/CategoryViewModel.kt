package pe.fernan.apps.compyble.ui.screen.category

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.fernan.apps.compyble.domain.useCase.GetCategoriesUseCase
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {
    val getCategories = getCategoriesUseCase.invoke()

}