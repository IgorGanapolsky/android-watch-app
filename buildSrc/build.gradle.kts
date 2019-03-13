plugins {
    `kotlin-dsl`
}

// More info: https://docs.gradle.org/4.10.1/userguide/kotlin_dsl.html#sec:kotlin-dsl_plugin
repositories {
    jcenter()
}

// More info : https://stackoverflow.com/a/46440810/2085356
// More info : https://github.com/gradle/kotlin-dsl/issues/443
sourceSets {
    getByName("main").java.srcDir("src/main/java")
    getByName("test").java.srcDir("src/test/kotlin")
}
