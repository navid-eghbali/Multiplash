package navid.multiplash.kodein.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.type.TypeToken
import org.kodein.type.erased
import kotlin.reflect.KClass

class KodeinViewModelScopedFactory<A : Any>(
    private val di: DI,
    private val argType: TypeToken<A>,
    private val arg: A,
    private val tag: String? = null,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        di.direct.Factory(argType, erased(modelClass), tag).invoke(arg)
}

class KodeinViewModelScopedSingleton(
    private val di: DI,
    private val tag: String? = null,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
        di.direct.Instance(type = erased(modelClass), tag = tag)
}
