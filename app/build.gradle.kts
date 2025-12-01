plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.takecare"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.takecare"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4" // usa la versión más reciente
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("com.github.jeziellago:compose-markdown:0.5.0")

    implementation("io.coil-kt:coil-compose:2.6.0")

    implementation("com.composables:core:1.47.1")

    implementation("com.microsoft.signalr:signalr:7.0.5")

    implementation("androidx.datastore:datastore-preferences:1.1.1")

    implementation("com.google.code.gson:gson:2.11.0")

    // Retrofit para peticiones HTTP
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // Converter para JSON con Gson
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // OkHttp para manejo de red y logging
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation(libs.volley)

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}