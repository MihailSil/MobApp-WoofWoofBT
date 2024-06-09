plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.woofwoofbitola"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.woofwoofbitola"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {

    implementation(libs.room.common)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    val room_version = "2.5.0"

    implementation ("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:2.5.0")

    // Optional - RxJava2 support for Room
    implementation ("androidx.room:room-rxjava2:$room_version")

    // Optional - Paging 3 Integration
    implementation ("androidx.room:room-paging:$room_version")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
}

