package navid.multiplash

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import navid.multiplash.shared.App
import navid.multiplash.shared.di.AndroidApplicationComponent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val applicationComponent = AndroidApplicationComponent.from(this)
        println("BOOGH> ${applicationComponent.dispatchers.main}")

        setContent {
            App(dynamicColor = true)
        }
    }
}

private fun AndroidApplicationComponent.Companion.from(context: Context): AndroidApplicationComponent =
    (context.applicationContext as MultiplashApplication).applicationComponent
