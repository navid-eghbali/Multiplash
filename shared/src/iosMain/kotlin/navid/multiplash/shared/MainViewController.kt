package navid.multiplash.shared

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    App(
        dynamicColor = false,
        modifier = Modifier
    )
}
