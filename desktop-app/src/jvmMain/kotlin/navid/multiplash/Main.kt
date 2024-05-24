package navid.multiplash

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import navid.multiplash.shared.App
import navid.multiplash.shared.di.DesktopApplicationComponent
import navid.multiplash.shared.di.create

fun main() = application {
    val applicationComponent = remember {
        DesktopApplicationComponent.create()
    }
    LaunchedEffect(applicationComponent) {
        println("BOOGH> ${applicationComponent.dispatchers.main}")
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Multiplash",
    ) {
        App(dynamicColor = false)
    }
}