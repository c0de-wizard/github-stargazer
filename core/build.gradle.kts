import com.thomaskioko.stargazers.dependencies.Dependencies

plugins {
    `android-library-plugin`
}

dependencies {

    implementation(Dependencies.timber)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.Lifecycle.common)
    implementation(Dependencies.AndroidX.Lifecycle.runtime)
    implementation(Dependencies.AndroidX.Lifecycle.viewmodel)

    implementation(Dependencies.Coroutines.android)
}
