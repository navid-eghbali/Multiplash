import com.android.build.gradle.BaseExtension
import com.android.build.gradle.BasePlugin

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
}

subprojects {
    plugins.withType<BasePlugin>().configureEach {
        extensions.configure<BaseExtension> {
            compileSdkVersion(libs.versions.android.compile.sdk.get().toInt())
            defaultConfig {
                minSdk = libs.versions.android.min.sdk.get().toInt()
                targetSdk = libs.versions.android.target.sdk.get().toInt()
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}
