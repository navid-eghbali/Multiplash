package navid.multiplash

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import navid.multiplash.shared.App
import java.awt.Toolkit

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            size = getWindowSize(),
        ),
        title = "Multiplash",
    ) {
        App(
            dynamicColor = false,
            modifier = Modifier,
        )
    }
}

private fun getWindowSize(): DpSize {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    return DpSize(
        width = (screenSize.width * 0.8).toInt().dp,
        height = (screenSize.height * 0.8).toInt().dp,
    )
}
