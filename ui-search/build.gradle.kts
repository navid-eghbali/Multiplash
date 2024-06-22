plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
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
            implementation(projects.coreApi)
            implementation(projects.coreAsync)
            implementation(projects.coreDi)
            implementation(projects.coreUi)
            implementation(projects.kodeinViewmodel)

            implementation(libs.coil.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
        }
    }
}

android {
    namespace = "navid.multiplash.ui.search"
}
