import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    androidLibrary {
       namespace = "com.example.chessclock.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
//            implementation("androidx.compose.ui:ui-text-google-fonts:1.11.1")
//            val lifecycle_version = "2.9.2" // Or the latest stable version
//            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
//
//            implementation("androidx.compose.material:material-icons-extended:1.7.8")
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
//            implementation("androidx.navigation:navigation-compose:2.7.7")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}