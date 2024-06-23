import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.build.konfig)
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
            implementation(projects.core.di)

            api(libs.ktor.client.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.json)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
    }
}

android {
    namespace = "navid.multiplash.core.api"
}

buildkonfig {
    packageName = "navid.multiplash.core.api"
    defaultConfigs {
        buildConfigField(STRING, "UNSPLASH_CLIENT_ID", getApiKey())
    }
}

fun getApiKey(): String {
    val propertiesFile = project.rootProject.file("api.properties")
    val properties = Properties()
    properties.load(propertiesFile.inputStream())
    return properties.getProperty("UNSPLASH_ACCESS_KEY")
}
