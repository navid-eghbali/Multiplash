package navid.multiplash.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import navid.multiplash.core.ui.designsystem.AppDarkColors
import navid.multiplash.core.ui.designsystem.AppLightColors
import navid.multiplash.core.ui.designsystem.AppShapes

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) AppDarkColors else AppLightColors,
        shapes = AppShapes,
        content = content
    )
}
