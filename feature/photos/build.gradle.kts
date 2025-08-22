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
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.di)
            implementation(projects.core.resources)
            implementation(projects.core.ui)

            implementation(projects.feature.search.api)

            implementation(libs.coil.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.paging.common)
            implementation(libs.paging.compose)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
        }
    }
}

android {
    namespace = "navid.multiplash.feature.photos"
}
