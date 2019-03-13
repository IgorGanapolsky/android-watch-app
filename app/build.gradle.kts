import org.gradle.internal.impldep.com.amazonaws.PredefinedClientConfigurations.defaultConfig
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdkVersion(Versions.compile_sdk)
    defaultConfig {
        applicationId = "com.igorganapolsky.vibratingwatchapp"
        minSdkVersion(Versions.min_sdk)
        targetSdkVersion(Versions.target_sdk)
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

//    sourceSets {
//        getByName("main").java.srcDir("src/main/kotlin")
//        getByName("test").java.srcDir("src/test/kotlin")
//    }

    // For Roboelectric.
    testOptions.unitTests.setIncludeAndroidResources(true)
}

// Kotlin.
dependencies {
    implementation(Deps.kotlin_stdlib_jdk8)
}

// Add Architecture Components.
dependencies {
    implementation(Deps.room_runtime)
    kapt(Deps.room_compiler)
    implementation(Deps.arch_comp)
    kapt(Deps.arch_comp_annotation)
}

// Design.
dependencies {
    implementation(Deps.material_design)
    implementation(Deps.vector_drawable)
    implementation(Deps.recycler_view)
}

// GMS APIs (Places API, Fused Location Provider).
dependencies {
    implementation(Deps.gms_places)
    implementation(Deps.gms_location)
}

// GSON.
dependencies {
    implementation(Deps.gson)
}

// Dagger 2.
run {
    // Dagger 2 and Kotlin docs - https://kotlinlang.org/docs/tutorials/android-frameworks.html
    // Dagger 2 and Android (Java) - https://kotlinlang.org/docs/tutorials/android-frameworks.html
    dependencies {
        // Basic Dagger 2 (required).
        implementation(Deps.dagger2)
        kapt(Deps.dagger2_annotation)
    }
}

// Colorful console.
dependencies {
    implementation("com.importre:crayon:0.1.0")
}


// Testing w/ JUnit5 & AssertJ.
run {

    dependencies {
        // Add JUnit5 dependencies.
        testImplementation(TestingDeps.junit5_jupiter)
        testRuntimeOnly(TestingDeps.junit5_jupiter_runtime)
        testImplementation(TestingDeps.junit5_jupiter_params)

        // Add JUnit4 legacy dependencies.
        testImplementation(TestingDeps.junit4_legacy)
        testRuntimeOnly(TestingDeps.junit5_vintage)

        // Add AssertJ dependencies.
        testImplementation(TestingDeps.assertj)

        // Add MockK dependencies.
        testImplementation(TestingDeps.mockk)

        // Add Roboelectric dependencies.
        testImplementation(TestingDeps.roboelectric)
    }

    // Need this to use Java8 in order to use certain features of JUnit5 (such as calling static
    // methods on interfaces).

    // More info : https://github.com/mannodermaus/android-junit5/wiki/Getting-Started
    // More info : https://stackoverflow.com/a/45994990/2085356

    // For Kotlin sources.
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    // For Java sources.
    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
