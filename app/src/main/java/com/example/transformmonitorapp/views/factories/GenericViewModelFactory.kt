import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GenericViewModelFactory<T : ViewModel>(
    private val creator: () -> T
) : ViewModelProvider.Factory {

    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {

        if (modelClass.isAssignableFrom(creator().javaClass)) {
            @Suppress("UNCHECKED_CAST")
            return creator() as VM
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}