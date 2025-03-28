plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.transit_ags"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.transit_ags"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.room.common)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //usar el mapa


    implementation("com.google.maps.android:maps-compose:4.1.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    implementation ("androidx.compose.material3:material3:1.0.0") // Para Material Design 3
    implementation ("androidx.navigation:navigation-compose:2.5.0")
    implementation("androidx.compose.material:material:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation ("androidx.compose.ui:ui:1.4.0") // Usa la última versión estable de Compose

    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation ("com.google.zxing:core:3.5.1")
    implementation ("com.auth0:java-jwt:4.4.0")


}