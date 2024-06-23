plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget()
    jvm("desktop")
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(libs.kodein.di)
            api(libs.kodein.di.conf)
            api(libs.kodein.di.framework.compose)
        }
    }
}

android {
    namespace = "navid.multiplash.core.di"
}
