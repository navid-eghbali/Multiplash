package navid.multiplash

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import navid.multiplash.shared.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Multiplash",
    ) {
        App(
            dynamicColor = false,
            modifier = Modifier,
        )
    }
}
