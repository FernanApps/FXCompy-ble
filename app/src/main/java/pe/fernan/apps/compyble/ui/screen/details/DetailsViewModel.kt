package pe.fernan.apps.compyble.ui.screen.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import pe.fernan.apps.compyble.domain.model.Details
import pe.fernan.apps.compyble.domain.model.Product
import pe.fernan.apps.compyble.domain.useCase.GetDetailsUseCase
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsUseCase: GetDetailsUseCase
) : ViewModel(){

    var details = emptyFlow<Details>()


    fun getDetails(path: String){
        viewModelScope.launch {
            details = detailsUseCase.invoke(path)
        }
    }
    
}