plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm()
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
