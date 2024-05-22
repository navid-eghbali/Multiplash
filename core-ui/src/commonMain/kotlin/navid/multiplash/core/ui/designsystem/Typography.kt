package navid.multiplash.core.ui.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import multiplash.core_ui.generated.resources.Res
import multiplash.core_ui.generated.resources.montserrat_bold
import multiplash.core_ui.generated.resources.montserrat_light
import multiplash.core_ui.generated.resources.montserrat_medium
import multiplash.core_ui.generated.resources.montserrat_regular
import org.jetbrains.compose.resources.Font

val AppTypography: Typography
    @Composable get() {
        return MaterialTheme.typography.copy(
            displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = fontFamily),
            displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = fontFamily),
            displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = fontFamily),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = fontFamily),
            titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = fontFamily),
            titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = fontFamily),
            titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = fontFamily),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = fontFamily),
            bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = fontFamily),
            labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = fontFamily),
            labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = fontFamily),
            labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = fontFamily)
        )
    }

private val fontFamily: FontFamily
    @Composable get() = FontFamily(
        Font(resource = Res.font.montserrat_light, weight = FontWeight.Light),
        Font(resource = Res.font.montserrat_regular, weight = FontWeight.Normal),
        Font(resource = Res.font.montserrat_medium, weight = FontWeight.Medium),
        Font(resource = Res.font.montserrat_bold, weight = FontWeight.Bold),
    )
