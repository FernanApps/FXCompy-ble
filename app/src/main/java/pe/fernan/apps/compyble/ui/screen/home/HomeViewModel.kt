package pe.fernan.apps.compyble.ui.screen.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.fernan.apps.compyble.domain.useCase.GetHomeDataUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getHomeDataUseCase: GetHomeDataUseCase,
) : ViewModel() {
    val getData = getHomeDataUseCase.invoke()

}