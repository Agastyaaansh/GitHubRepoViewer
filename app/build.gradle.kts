plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt") // This is the correct way to apply kapt in Kotlin DSL
    id ("com.google.dagger.hilt.android")

}

android {
    namespace = "com.example.githubrepos"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.githubrepos"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
    }
}





dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // app/build.gradle

        // Core
        implementation ("androidx.core:core-ktx:1.10.1")
        implementation ("androidx.appcompat:appcompat:1.6.1")
        implementation ("com.google.android.material:material:1.9.0")
        implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

        // Lifecycle & ViewModel
        implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
        implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
        implementation ("androidx.fragment:fragment-ktx:1.6.1")

        // Navigation
        implementation ("androidx.navigation:navigation-fragment-ktx:2.6.0")
        implementation ("androidx.navigation:navigation-ui-ktx:2.6.0")

        // Retrofit
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

        // Coroutines
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

        // Hilt
        implementation ("com.google.dagger:hilt-android:2.48")
        kapt ("com.google.dagger:hilt-compiler:2.48")

        // Glide
        implementation ("com.github.bumptech.glide:glide:4.16.0")
        kapt ("com.github.bumptech.glide:compiler:4.16.0")

        // Paging
        implementation ("androidx.paging:paging-runtime-ktx:3.2.0")

        // Testing
        testImplementation ("junit:junit:4.13.2")
        androidTestImplementation ("androidx.test.ext:junit:1.1.5")
        androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")


    // Add these to ensure proper annotation processing
    annotationProcessor ("com.google.dagger:hilt-android-compiler:2.48")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.48")
    kaptTest ("com.google.dagger:hilt-android-compiler:2.48")

}

kapt {
    correctErrorTypes = true
}