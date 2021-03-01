plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        minSdkVersion(BuildVersions.minSdkVersion)
        compileSdkVersion(BuildVersions.compileSdkVersion)
        targetSdkVersion(BuildVersions.targetSdkVersion)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = false
            versionNameSuffix = "-DEBUG"
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    sourceSets {
        val androidTest by getting
        val test by getting
        androidTest.java.srcDirs("src/androidTest/kotlin")
        test.java.srcDirs("src/test/kotlin")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":common-ui"))
    implementation(project(":navigation"))
    implementation(project(":repository"))

    implementation(Dependencies.Kotlin.stdlib)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.Lifecycle.viewmodel)

    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ktx)
    implementation(Dependencies.AndroidX.Navigation.runtime)

    implementation(Dependencies.Google.Hilt.core)
    implementation(Dependencies.Google.Hilt.viewmodel)
    kapt(Dependencies.Google.Hilt.compiler)

    implementation(Dependencies.Google.material)
    implementation(Dependencies.timber)

    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Coroutines.android)

    testImplementation(Dependencies.Testing.junit)
    testImplementation(Dependencies.Testing.truth)
    testImplementation(Dependencies.Testing.turbine)
    testImplementation(Dependencies.Testing.mockitoKotlin)
    testImplementation(Dependencies.Testing.Mockito.core)
    testImplementation(Dependencies.Testing.AndroidX.core)
    testImplementation(Dependencies.Testing.Coroutines.test)

    androidTestImplementation(Dependencies.Testing.AndroidX.junit)
    androidTestImplementation(Dependencies.Testing.AndroidX.fragment)
    androidTestImplementation(Dependencies.Testing.androidEspressoCore)

}