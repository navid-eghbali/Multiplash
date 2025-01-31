package navid.multiplash.shared.di

import navid.multiplash.feature.details.usecase.SaveToFileUseCase
import org.kodein.di.DI
import org.kodein.di.bindProvider

internal actual val platformModule = DI.Module(name = "PlatformModule") {

    bindProvider { SaveToFileUseCase() }

}
