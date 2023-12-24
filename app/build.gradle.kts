plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization")
}

private val appVersionCode = 1

private val appVersionName = "1.0"

android {
    namespace = "com.hogent.svkapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hogent.svkapp"
        minSdk = 30
        targetSdk = 34
        versionCode = appVersionCode
        versionName = appVersionName

        manifestPlaceholders["auth0Domain"] = "@string/com_auth0_domain"
        manifestPlaceholders["auth0Scheme"] = "@string/com_auth0_scheme"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.6")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    val coreVersion = "1.12.0"
    val lifecycleVersion = "2.6.2"
    val activityVersion = "1.8.2"
    val composeBomVersion = "2023.10.00"
    val composeUiVersion = "1.5.4"
    val composeMaterial3Version = "1.1.2"
    val composeNavigationVersion = "2.7.6"
    val roomVersion = "2.6.1"
    val retrofitVersion = "2.9.0"



    implementation("androidx.core:core-ktx:$coreVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-compose:$activityVersion")
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.ui:ui-graphics:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    implementation("androidx.compose.material3:material3:$composeMaterial3Version")
    implementation("androidx.compose.material3:material3-window-size-class:$composeMaterial3Version")
    implementation("androidx.navigation:navigation-runtime-ktx:$composeNavigationVersion")
    implementation("androidx.navigation:navigation-compose:$composeNavigationVersion")

    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation("com.auth0.android:auth0:2.10.2")
    implementation("com.auth0.android:jwtdecode:2.0.2")
    implementation("androidx.room:room-runtime:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-scalars:$retrofitVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

// Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    val testJunitVersion = "1.1.5"
    val espressoVersion = "3.5.1"
    val testRunnerVersion = "1.5.2"
    val testRulesVersion = "1.5.0"

    // CameraX
    val camerax_version = "1.3.1"

    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-view:${camerax_version}")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    androidTestImplementation("androidx.test.ext:junit:$testJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("androidx.test:rules:$testRulesVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUiVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.6")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")

    val junitVersion = "4.13.2"
    val mockitoKotlinVersion = "5.2.1"

    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
}
