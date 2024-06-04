package navid.multiplash.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import navid.multiplash.core.ui.AppTheme
import navid.multiplash.ui.home.HomeScreen

@Composable
fun App(
    dynamicColor: Boolean,
    modifier: Modifier = Modifier,
) {
    AppTheme(
        darkTheme = isSystemInDarkTheme(),
        dynamicColor = dynamicColor
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) {
            HomeScreen(modifier = Modifier.padding(it))
        }
    }
}
