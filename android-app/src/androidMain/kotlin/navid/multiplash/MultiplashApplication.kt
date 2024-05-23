package navid.multiplash

import android.app.Application
import navid.multiplash.shared.di.AndroidApplicationComponent
import navid.multiplash.shared.di.create

class MultiplashApplication : Application() {

    val applicationComponent: AndroidApplicationComponent by lazy(LazyThreadSafetyMode.NONE) {
        AndroidApplicationComponent.create(this)
    }
}
