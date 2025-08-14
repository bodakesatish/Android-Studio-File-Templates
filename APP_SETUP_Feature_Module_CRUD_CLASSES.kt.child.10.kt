package ${PACKAGE_NAME}

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ${NAME}Application : Application()

/*

   <item name="android:windowOptOutEdgeToEdgeEnforcement" tools:ignore="NewApi">true</item>
   
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    
    
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.navigation.safeargs)
}

android {
    // Keep rest as it is
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        //compose = true
        aidl = false
        buildConfig = false
        renderScript = false
        shaders = false
        viewBinding = true
    }
}

dependencies {

    // Using bundles
    // Core, Appcompat, Material, Activity, ConstraintLayout
    implementation(libs.bundles.androidx.ui)

    // Navigation Fragment & UI KTX
    implementation(libs.bundles.navigation)

    // Room Runtime & KTX
    implementation(libs.bundles.room)

    // Firebase Auth & Firestore
    implementation(libs.bundles.firebase)

    // Individual dependencies that don't fit neatly into the above bundles or are standalone
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.swiperefreshlayout)

    // KSP for Room Compiler
    ksp(libs.androidx.room.compiler)

    // Hilt (Compiler is ksp, runtime is implementation)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Testing - using bundles
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.android)
}
   [versions]
agp = "8.11.1"
kotlin = "2.0.21"
coreKtx = "1.16.0"
junit = "4.13.2"
junitVersion = "1.2.1"
espressoCore = "3.6.1"
appcompat = "1.7.1"
material = "1.12.0"
activity = "1.10.1"
constraintlayout = "2.2.1"

lifecycleViewmodelKtx = "2.8.7"
navigationFragmentKtx = "2.8.9"
navigationUiKtx = "2.8.9"

googleGmsGoogleServices = "4.4.3"
ksp = "2.1.20-2.0.0"
hilt = "2.56.1"
androidxRoom = "2.7.0"
firebaseAuthKtx = "23.2.1"
firebaseFirestoreKtx = "25.1.4"
navigationRuntimeKtx = "2.8.7"
swiperefreshlayout = "1.1.0"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
material = { group = "com.google.android.material", name = "material", version.ref = "material" }
androidx-activity = { group = "androidx.activity", name = "activity", version.ref = "activity" }
androidx-constraintlayout = { group = "androidx.constraintlayout", name = "constraintlayout", version.ref = "constraintlayout" }

androidx-lifecycle-viewmodel-ktx = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-ktx", version.ref = "lifecycleViewmodelKtx" }
androidx-navigation-fragment-ktx = { group = "androidx.navigation", name = "navigation-fragment-ktx", version.ref = "navigationFragmentKtx" }
androidx-navigation-ui-ktx = { group = "androidx.navigation", name = "navigation-ui-ktx", version.ref = "navigationUiKtx" }

hilt-android = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
hilt-compiler = { module = "com.google.dagger:hilt-compiler", version.ref = "hilt" }

androidx-room-runtime = { module = "androidx.room:room-runtime", version.ref = "androidxRoom" }
androidx-room-ktx = { module = "androidx.room:room-ktx", version.ref = "androidxRoom" }
androidx-room-compiler = { module = "androidx.room:room-compiler", version.ref = "androidxRoom" }
firebase-auth-ktx = { group = "com.google.firebase", name = "firebase-auth-ktx", version.ref = "firebaseAuthKtx" }
firebase-firestore-ktx = { group = "com.google.firebase", name = "firebase-firestore-ktx", version.ref = "firebaseFirestoreKtx" }
androidx-swiperefreshlayout = { group = "androidx.swiperefreshlayout", name = "swiperefreshlayout", version.ref = "swiperefreshlayout" }


[bundles]
# Navigation Components
navigation = ["androidx-navigation-fragment-ktx", "androidx-navigation-ui-ktx"] # Add safe-args plugin here if you wish, though plugins are usually separate

# Room Persistence Library
room = ["androidx-room-runtime", "androidx-room-ktx"] # Compiler is applied via ksp, so not typically in an implementation bundle

# Firebase (Auth and Firestore)
firebase = ["firebase-auth-ktx", "firebase-firestore-ktx"]

# Common Android UI Toolkit (Core, Appcompat, Material, ConstraintLayout, Activity)
# You could break this down further if preferred
androidx-ui = ["androidx-core-ktx", "androidx-appcompat", "material", "androidx-activity", "androidx-constraintlayout"]

# Hilt - usually applied individually (implementation and ksp), but if you had multiple hilt runtime deps, you could bundle them.
# For now, let's keep hilt separate as one is 'implementation' and the other is 'ksp'

# Testing (JUnit and Espresso) - Note: separate bundles for testImplementation and androidTestImplementation might be clearer
testing-unit = ["junit"]
testing-android = ["androidx-junit", "androidx-espresso-core"]


[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
google-gms-google-services = { id = "com.google.gms.google-services", version.ref = "googleGmsGoogleServices" }
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp"}
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
androidx-navigation-safeargs = { id = "androidx.navigation.safeargs.kotlin", version.ref = "navigationRuntimeKtx"}
kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
 */