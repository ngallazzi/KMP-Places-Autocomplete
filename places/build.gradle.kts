import com.vanniktech.maven.publish.SonatypeHost
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version ("2.0.20")
    id("com.github.gmazzo.buildconfig") version "5.4.0"
    id("com.vanniktech.maven.publish") version "0.28.0"
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
        publishLibraryVariants("release", "debug")
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
            implementation(compose.material)
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

mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        groupId = "io.github.ngallazzi",
        artifactId = "KMP-Places-Autocomplete",
        version = "1.0.1"
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("KMP-Places-Autocomplete")
        description.set("A simple Compose Multiplatform library to fill addresses, cities and countries in a form, based on Google Places API by Google https://developers.google.com/maps/documentation/places/web-service/autocomplete. For Android and IOS")
        inceptionYear.set("2024")
        url.set("https://github.com/ngallazzi/KMP-Places-Autocomplete")

        licenses {
            license {
                name.set("GNU GENERAL PUBLIC LICENSE")
                url.set("https://www.gnu.org/licenses/gpl-3.0.en.html#license-text")
            }
        }

        // Specify developers information
        developers {
            developer {
                id.set("ngallazzi")
                name.set("Nicola Gallazzi")
                email.set("nicola.gallazzi.dev@gmail.com")
            }
        }

        // Specify SCM information
        scm {
            url.set("https://github.com/ngallazzi/KMP-Places-Autocomplete/blob/main/README.md")
        }
    }

    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable GPG signing for all publications
    signAllPublications()
}

