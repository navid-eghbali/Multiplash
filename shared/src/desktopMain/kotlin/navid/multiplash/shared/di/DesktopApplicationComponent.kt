package navid.multiplash.shared.di

import me.tatarka.inject.annotations.Component
import navid.multiplash.core.di.ApplicationScope

@Component
@ApplicationScope
abstract class DesktopApplicationComponent : SharedApplicationComponent {

    companion object
}
