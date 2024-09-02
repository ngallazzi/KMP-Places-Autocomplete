import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version ("2.0.20")
    id("com.github.gmazzo.buildconfig") version "5.4.0"
    `maven-publish`
    signing // Optional: for signing artifacts
}

buildConfig {
    // Load the properties from local.properties
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(FileInputStream(localPropertiesFile))
        val apiKey = localProperties.getProperty("api_key")
        buildConfigField("API_KEY", apiKey)
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    task("testClasses")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "places"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
            implementation("io.ktor:ktor-client-core:2.3.5")
            implementation("io.ktor:ktor-client-serialization:2.3.5")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
            implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
        }
        commonTest.dependencies {
            implementation("io.insert-koin:koin-test:3.5.3")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
        }
        androidMain.dependencies {
            implementation("io.ktor:ktor-client-okhttp:2.3.5")
        }
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:2.3.5")
        }
        commonTest.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
            implementation("io.ktor:ktor-client-mock:2.3.5")
        }
    }
}

publishing {
    repositories {
        maven {
            name = "MavenCentral"
            url = uri("https://maven.pkg.github.com/ngallazzi/KMP-Places-Autocomplete") // Your Maven repository URL
            credentials(PasswordCredentials::class)
            authentication{
                create<BasicAuthentication>("basic")
            }
        }
    }
}



android {
    namespace = "com.ngallazzi.places"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
dependencies {
    implementation(libs.androidx.runtime.android)
}

