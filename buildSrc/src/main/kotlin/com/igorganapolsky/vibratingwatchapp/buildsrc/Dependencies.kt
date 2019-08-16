package com.igorganapolsky.vibratingwatchapp.buildsrc

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:3.6.0-alpha06"
    const val dexcountGradlePlugin = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:0.8.6"
    const val playPublisherPlugin = "com.github.triplet.gradle:play-publisher:2.2.1"

    val mvRx = "com.airbnb.android:mvrx:1.0.1"

    val threeTenBp = "org.threeten:threetenbp:1.4.0"
    val threeTenBpNoTzdb = "$threeTenBp:no-tzdb"
    val threeTenAbp = "com.jakewharton.threetenabp:threetenabp:1.2.1"

    val gravitySnapHelper = "com.github.rubensousa:gravitysnaphelper:2.0"

    val rxLint = "nl.littlerobots.rxlint:rxlint:1.7.4"

    val timber = "com.jakewharton.timber:timber:4.7.1"

    val junit = "junit:junit:4.12"
    val robolectric = "org.robolectric:robolectric:4.3"
    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"

    object Google {
        val material = "com.google.android.material:material:1.1.0-alpha07"
        val firebaseCore = "com.google.firebase:firebase-core:16.0.9"
        val crashlytics = "com.crashlytics.sdk.android:crashlytics:2.10.1@aar"
        val gmsGoogleServices = "com.google.gms:google-services:4.2.0"
        val fabricPlugin = "io.fabric.tools:gradle:1.+"
        val play_services_wearable = "com.google.android.gms:play-services-play_services_wearable:17.0.0"
    }

    object Wear {
        val wear = "androidx.wear:wear:1.0.0"
        val wear_implementation = "com.google.android.support:wearable:2.4.0"
        val wear_compile_only = "com.google.android.support:wearable:2.4.0"
    }

    object Kotlin {
        private const val version = "1.3.41"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.3.0-M1"
        val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
        val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object AndroidX {
        private const val rvVersion = "1.1.0-alpha06"
        private const val version = "1.0.0"
        val recyclerview = "androidx.recyclerview:recyclerview:$rvVersion"
        val recyclerviewSelection = "androidx.recyclerview:recyclerview-selection:$rvVersion"
        val appcompat = "androidx.appcompat:appcompat:1.1.0-beta01"
        val palette = "androidx.palette:palette:$version"
        val emoji = "androidx.emoji:emoji:$version"
        val percentLayout = "androidx.percentlayout:percentlayout:$version"
        val legacySupport = "androidx.legacy:legacy-support-v4:$version"
        val annotate = "androidx.annotation:annotation:1.1.0"

        object Navigation {
            private const val version = "2.0.0"
            val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            val ui = "androidx.navigation:navigation-ui-ktx:$version"
            val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        }

        object Fragment {
            private const val version = "1.1.0-beta01"
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

        val preference = "androidx.preference:preference:1.1.0-beta01"

        const val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"

        val coreKtx = "androidx.core:core-ktx:1.2.0-alpha02"

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
            val rxjava2 = "androidx.room:room-rxjava2:$version"
            val compiler = "androidx.room:room-compiler:$version"
            val ktx = "androidx.room:room-ktx:$version"
            val testing = "androidx.room:room-testing:$version"
        }

        object Work {
            private const val version = "2.0.1"
            val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
        }
    }

    object RxJava {
        val rxJava = "io.reactivex.rxjava2:rxjava:2.2.9"
        val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"
        val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    }

    object Dagger {
        private const val version = "2.23.2"
        val dagger = "com.google.dagger:dagger:$version"
        val androidSupport = "com.google.dagger:dagger-android-support:$version"
        val compiler = "com.google.dagger:dagger-compiler:$version"
        val androidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }

    object Glide {
        private const val version = "4.9.0"
        val glide = "com.github.bumptech.glide:glide:$version"
        val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Retrofit {
        private const val version = "2.6.0"
        val retrofit = "com.squareup.retrofit2:retrofit:$version"
        val retrofit_rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:$version"
        val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
    }

    object OkHttp {
        private const val version = "3.14.2"
        val okhttp = "com.squareup.okhttp3:okhttp:$version"
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Epoxy {
        private const val version = "3.6.0"
        val epoxy = "com.airbnb.android:epoxy:$version"
        val paging = "com.airbnb.android:epoxy-paging:$version"
        val dataBinding = "com.airbnb.android:epoxy-databinding:$version"
        val processor = "com.airbnb.android:epoxy-processor:$version"
    }

    object AssistedInject {
        private const val version = "0.4.0"
        val annotationDagger2 = "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
        val processorDagger2 = "com.squareup.inject:assisted-inject-processor-dagger2:$version"
    }
}
