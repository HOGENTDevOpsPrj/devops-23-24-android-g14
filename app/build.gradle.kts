plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.5")
    val coreVersion = "1.12.0"
    val lifecycleVersion = "2.6.2"
    val activityVersion = "1.8.0"
    val composeBomVersion = "2023.10.00"
    val composeUiVersion = "1.5.4"
    val composeMaterial3Version = "1.1.2"
    val composeNavigationVersion = "2.7.4"

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

    implementation("com.auth0.android:auth0:+")
    implementation("com.auth0.android:jwtdecode:+")

    val testJunitVersion = "1.1.5"
    val espressoVersion = "3.5.1"
    val testRunnerVersion = "1.5.2"
    val testRulesVersion = "1.5.0"

    androidTestImplementation("androidx.test.ext:junit:$testJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
    androidTestImplementation("androidx.test:rules:$testRulesVersion")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUiVersion")

    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")

    val junitVersion = "4.13.2"
    val mockitoKotlinVersion = "5.1.0"

    testImplementation("junit:junit:$junitVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
}
