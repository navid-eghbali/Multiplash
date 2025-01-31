package navid.multiplash.shared.di

import navid.multiplash.feature.details.usecase.SaveToFileUseCase
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal actual val platformModule = DI.Module(name = "PlatformModule") {

    bindProvider { SaveToFileUseCase(context = instance()) }

}
