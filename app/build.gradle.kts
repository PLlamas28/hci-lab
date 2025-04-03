plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.broken_test_3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.broken_test_3"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.wear.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.androidx.material.icons.extended)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    //implementation (libs.lifecycle.viewmodel.compose) // libs.lifecycle.viewmodel.compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")  //implementation (libs.lifecycle.runtime.compose) // libs.lifecycle.runtime.compose

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4") // Latest version
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation (libs.androidx.runtime) // Adjust version as needed
    implementation (libs.androidx.ui) // Required for Jetpack Compose

    //implementation (libs.xusers.peter.downloads.samsung.health.sdk.for1.android.x0241205.data.x.x.x.libs.samsung.health.data.x.x.x.aar)
    //implementation (files("C:/Users/peter/Downloads/samsung-health-sdk-for-android-20241205/data-1.5.1/libs/samsung-health-data-1.5.1.aar"))
    implementation (files("C:/Users/peter/Downloads/samsung-health-sensor-sdk_v1.3.0/1.3.0/libs/samsung-health-sensor-api-v1.3.0.aar"))
}