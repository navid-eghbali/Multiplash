package navid.multiplash.core.resources

import androidx.compose.runtime.staticCompositionLocalOf
import org.jetbrains.compose.resources.StringResource

expect fun platformStrings(): PlatformStrings

interface PlatformStrings {
    val savePhotoSucceed: StringResource
}

val LocalPlatformStrings = staticCompositionLocalOf<PlatformStrings> { error("No strings available!") }
