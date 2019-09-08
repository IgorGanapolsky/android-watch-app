package com.igorganapolsky.vibratingwatchapp.buildsrc

object Libs {
    private const val androidGradlePlugin = "com.android.tools.build:gradle:3.6.0-alpha10"
    private const val dexcountGradlePlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:0.8.6"
    private const val playPublisherPlugin = "com.github.triplet.gradle:play-publisher:2.2.1"
    private const val mvRx = "com.airbnb.android:mvrx:1.0.1"
    private const val threeTenBp = "org.threeten:threetenbp:1.4.0"
    private const val threeTenBpNoTzdb = "$threeTenBp:no-tzdb"
    private const val threeTenAbp = "com.jakewharton.threetenabp:threetenabp:1.2.1"
    private const val gravitySnapHelper = "com.github.rubensousa:gravitysnaphelper:2.0"
    private const val timber = "com.jakewharton.timber:timber:4.7.1"
    private const val junit = "junit:junit:4.12"
    private const val buildSrcVersions = "de.fayard.buildSrcVersions:de.fayard.buildSrcVersions.gradle.plugin:0.4.2"
    private const val seekArc = "com.github.IgorGanapolsky:SeekArc:master-SNAPSHOT"

    object Google {
        const val material = "com.google.android.material:material:1.1.0-alpha09"
        const val firebaseCore = "com.google.firebase:firebase-core:16.0.9"
        const val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1@aar"
        const val gmsGoogleServices = "com.google.gms:google-services:4.2.0"
        const val fabricPlugin = "io.fabric.tools:gradle:1.+"
        const val play_services_wearable =
            "com.google.android.gms:play-services-play_services_wearable:17.0.0"
    }

    object Wear {
        const val wear = "androidx.wear:wear:1.0.0"
        const val wear_implementation = "com.google.android.support:wearable:2.5.0"
        const val wear_compile_only = "com.google.android.support:wearable:2.4.0"
    }

    object Kotlin {
        private const val version = "1.3.50"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"

        object Coroutines {
            private const val version = "1.3.0-M1"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }
    }

    object AndroidX {
        private const val rvVersion = "1.1.0-beta04"
        private const val version = "1.0.0"
        private const val recyclerview = "androidx.recyclerview:recyclerview:$rvVersion"
        private const val recyclerviewSelection = "androidx.recyclerview:recyclerview-selection:1.1.0-alpha06"
        private const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        private const val palette = "androidx.palette:palette:$version"
        private const val percentLayout = "androidx.percentlayout:percentlayout:$version"
        private const val legacySupport = "androidx.legacy:legacy-support-v4:$version"
        private const val annotate = "androidx.annotation:annotation:1.1.0"
        private val preference = "androidx.preference:preference:1.1.0-beta01"
        private const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta2"
        private val coreKtx = "androidx.core:core-ktx:1.2.0-alpha03"

        object Navigation {
            private const val version = "2.0.0"
            val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            val ui = "androidx.navigation:navigation-ui-ktx:$version"
            val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Activity {
            private const val version = "1.1.0-alpha02"
            val activityKtx = "androidx.activity:activity-ktx:$version"
        }

        object Fragment {
            private const val version = "1.2.0-alpha03"
            val fragment = "androidx.fragment:fragment:$version"
            val fragmentKtx = "androidx.fragment:fragment-ktx:$version"
        }

        object Test {
            private const val version = "1.3.0-alpha01"
            val runner = "androidx.test:runner:$version"
            val rules = "androidx.test:rules:$version"
            val core = "androidx.test:core:1.2.1-alpha01"
            val espressoCore = "androidx.test.espresso:espresso-core:3.3.0-alpha01"
            val archCoreTesting = "androidx.arch.core:core-testing:2.1.0-beta01"
        }


        object Paging {
            private const val version = "2.1.0"
            val common = "androidx.paging:paging-common:$version"
            val runtime = "androidx.paging:paging-runtime:$version"
            val rxjava2 = "androidx.paging:paging-rxjava2:$version"
        }

        object Lifecycle {
            private const val version = "2.2.0-alpha01"
            val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            val reactive = "androidx.lifecycle:lifecycle-reactivestreams-ktx:$version"
            val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
            val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Room {
            private const val version = "2.1.0"
            val common = "androidx.room:room-common:$version"
            val runtime = "androidx.room:room-runtime:$version"
            val compiler = "androidx.room:room-compiler:$version"
            val ktx = "androidx.room:room-ktx:$version"
            val testing = "androidx.room:room-testing:$version"
        }

        object Work {
            private const val version = "2.0.1"
            val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
        }
    }

}
