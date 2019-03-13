object GradlePlugins {
    data class Versions(
        val gradle: String = "3.3.2",
        val kotlin: String = "1.3.21",
        val junit5: String = "1.2.0.0"
    )

    val versions = Versions()

    val gradle = "com.android.tools.build:gradle:${versions.gradle}"

    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.kotlin}"

    val junit5 = "de.mannodermaus.gradle.plugins:android-junit5:${versions.junit5}"
}

object Versions {
    val compile_sdk = 28
    val target_sdk = 28
    val min_sdk = 24
}

object Deps {
    data class Versions(
        val support_wearable: String = "2.4.0",
        val services_wearable: String = "16.0.1",
        val constraint_layout: String = "1.1.3",
        val arch_comp: String = "2.0.0",
        val seekArc: String = "v1.1",
        val support_version: String = "28.0.0",
        val room_version: String = "1.1.1"
    )

    val versions = Versions()

    val kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${GradlePlugins.versions.kotlin}"

    val arch_comp = "android.arch.lifecycle:extensions:${versions.arch_comp}"
    val arch_comp_annotation = "androidx.lifecycle:lifecycle-compiler:${versions.arch_comp}"
    val room_runtime = "android.arch.persistence.room_version:runtime:${versions.room_version}"
    val room_compiler = "android.arch.persistence.room_version:compiler:${versions.room_version}"
    val support_wearable = "com.google.android.support_v7:wearable:${versions.support_wearable}"
    val services_wearable = "com.google.android.gms:play-services-support_wear:${versions.services_wearable}"
    val constraint_layout = "com.android.support_v7.constraint:${versions.constraint_layout}"
    val seekArc = "com.github.Triggertrap:SeekArc:${versions.seekArc}"

    val percent = "com.android.support_v7:percent:${versions.support_version}"
    val v7 = "com.android.support_v7:support_v7-v4:${versions.support_version}"
    val recyclerview = "com.android.support_v7:recyclerview-v7:${versions.support_version}"
    val recyclerview_selection = "com.android.support_v7:recyclerview-selection:${versions.support_version}"
    val wear = "com.android.support_v7:wear:${versions.support_version}"
    val appcompat = "com.android.support_v7:appcompat-v7:${versions.support_version}"
    val design = "com.android.support_v7:design:${versions.support_version}"
    val annotations = "com.android.support_v7:support_v7-annotations:${versions.support_version}"
}

object TestingDeps {
    data class Versions(
        val test_core: String = "1.1.0",
        val test_ext: String = "1.1.0",
        val arch_core_testing: String = "2.0.0",
        val test_runner: String = "1.1.1",
        val test_rules: String = "1.1.1",
        val espresso: String = "3.1.1",
        val truth: String = "1.1.0",
        val kluent: String = "1.14",
        val assertj: String = "3.11.1",
        val junit5: String = "5.2.0",
        val mockk: String = "1.8.9",
        val robolectric: String = "4.2",
        val junit4: String = "4.12"
    )

    val versions = Versions()

    val room_test = "android.arch.persistence.room_version:testing:${versions.room_version}"
    val test_core = "androidx.test:core:${versions.test_core}"
    val arch_core_testing = "androidx.arch.core:core-testing:${versions.arch_core_testing}"
    val junit4 = "androidx.test.ext:${versions.arch_core_testing}"
    val test_runner = "androidx.test:runner:${versions.test_runner}"
    val test_rules = "androidx.test:rules:${versions.test_rules}"
    val espresso_core = "androidx.test.espresso:espresso-core:${versions.espresso}"
    val espresso_intents = "androidx.test.espresso:espresso-intents:${versions.espresso}"
    val truth = "androidx.test.ext:truth:${versions.truth}"
    val kluent = "org.amshove.kluent:kluent:${versions.kluent}"

    val junit5_jupiter = "org.junit.jupiter:junit-jupiter-api:${versions.junit5}"
    val junit5_jupiter_runtime = "org.junit.jupiter:junit-jupiter-engine:${versions.junit5}"
    val junit5_jupiter_params = "org.junit.jupiter:junit-jupiter-params:${versions.junit5}"
    val junit4_legacy = "junit:junit:${versions.junit4}"
    val junit5_vintage = "org.junit.vintage:junit-vintage-engine:${versions.junit5}"

    val assertj = "org.assertj:assertj-core:${versions.assertj}"

    val mockk = "io.mockk:mockk:${versions.mockk}"

    val roboelectric = "org.robolectric:robolectric:${versions.robolectric}"

}

