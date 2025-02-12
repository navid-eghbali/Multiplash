package navid.multiplash.shared.di

import navid.multiplash.core.db.DatabaseBuilderFactory
import navid.multiplash.feature.details.usecase.SaveToFileUseCase
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton

internal actual val platformModule = DI.Module(name = "PlatformModule") {

    bindSingleton { DatabaseBuilderFactory() }

    bindProvider { SaveToFileUseCase() }

}
