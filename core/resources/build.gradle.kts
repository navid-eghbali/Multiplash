plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget()
    jvm("desktop")
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        androidMain.dependencies {
            implementation(compose.components.resources)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.components.resources)
        }
        iosMain.dependencies {
            implementation(compose.components.resources)
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.components.resources)
            }
        }
    }
}

android {
    namespace = "navid.multiplash.core.resources"
}

compose.resources {
    publicResClass = true
    packageOfResClass = "navid.multiplash.core.resources"
}
