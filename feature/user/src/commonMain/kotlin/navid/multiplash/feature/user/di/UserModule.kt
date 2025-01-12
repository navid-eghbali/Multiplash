package navid.multiplash.feature.user.di

import navid.multiplash.feature.user.ui.UserScreen
import navid.multiplash.feature.user.ui.UserViewModel
import org.kodein.di.DI
import org.kodein.di.bindFactory

val userModule = DI.Module(name = "UserModule") {
    bindFactory { args: UserScreen ->
        UserViewModel(
            args = args,
        )
    }
}
