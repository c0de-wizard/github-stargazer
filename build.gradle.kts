import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories.applyDefault()
}

allprojects {
    repositories.applyDefault()

    plugins.apply("plugins.ktlint")
    plugins.apply("plugins.detekt")
    plugins.apply("plugins.spotless")

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion(kotlinVersion)
            }
        }
    }
}

subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        with(kotlinOptions) {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            useIR = true
            languageVersion = "1.5"
            apiVersion = "1.5"
            freeCompilerArgs += "-Xuse-experimental=" +
                "kotlin.Experimental," +
                "kotlin.time.ExperimentalTime," +
                "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                "kotlinx.coroutines.InternalCoroutinesApi," +
                "kotlinx.coroutines.ObsoleteCoroutinesApi," +
                "kotlinx.coroutines.FlowPreview"
            freeCompilerArgs += "-Xopt-in=kotlin.ExperimentalStdlibApi"
        }
    }
}
