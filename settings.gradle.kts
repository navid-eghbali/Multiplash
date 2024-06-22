enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Multiplash"
include(
    ":android-app",
    ":core-api",
    ":core-async",
    ":core-di",
    ":core-ui",
    ":desktop-app",
    ":kodein-viewmodel",
    ":shared",
    ":ui-home",
    ":ui-search",
)
