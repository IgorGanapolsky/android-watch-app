// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    // More info on buildscript vs allprojects: https://stackoverflow.com/a/30159149/2085356
    dependencies {
        classpath(GradlePlugins.gradle)
        classpath(GradlePlugins.kotlin)
        classpath(GradlePlugins.junit5)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files.
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task("clean") {
    delete(rootProject.buildDir)
}
