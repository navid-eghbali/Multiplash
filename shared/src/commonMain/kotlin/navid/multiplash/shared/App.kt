package navid.multiplash.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import navid.multiplash.core.ui.AppTheme
import navid.multiplash.ui.home.HomeScreen

@Composable
fun App(
    dynamicColor: Boolean,
) {
    AppTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = dynamicColor
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            HomeScreen()
        }
    }
}
