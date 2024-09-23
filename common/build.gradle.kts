plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm("desktop")
    iosArm64()
    iosSimulatorArm64()
}
