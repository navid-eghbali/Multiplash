package navid.multiplash

import android.app.Application
import navid.multiplash.shared.di.appModule
import org.kodein.di.bindSingleton

class MultiplashApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        appModule.addConfig {
            bindSingleton { applicationContext }
        }
    }
}
