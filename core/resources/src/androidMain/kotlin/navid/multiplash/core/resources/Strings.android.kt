package navid.multiplash.core.resources

import org.jetbrains.compose.resources.StringResource

actual fun platformStrings(): PlatformStrings = object : PlatformStrings {
    override val savePhotoSucceed: StringResource = Res.string.save_photo_succeed
}
