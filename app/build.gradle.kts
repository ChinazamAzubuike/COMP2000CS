plugins {
    alias(libs.plugins.android.application)
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false




}

apply(plugin = "com.google.gms.google-services")

android {
    namespace = "com.example.comp2000cs"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.comp2000cs"
        minSdk = 29
        targetSdk = 34
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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.volley)

//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.analytics)
//   implementation(libs.firebase.messaging)
    implementation(libs.work.runtime)

    implementation(libs.annotation)

//    implementation(libs.gson)


}