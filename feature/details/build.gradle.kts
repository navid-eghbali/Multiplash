plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget()
    jvm("desktop")
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.common)
            implementation(projects.core.api)
            implementation(projects.core.data)
            implementation(projects.core.di)
            implementation(projects.core.resources)
            implementation(projects.core.ui)

            implementation(libs.coil.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
        }
        val desktopMain by getting
    }
}

android {
    namespace = "navid.multiplash.feature.details"
}
