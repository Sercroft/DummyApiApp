// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    extra.apply {
        set("retrofit_version", "2.9.0")
        set("coroutines_version", "1.3.9")
        set("dagger_hilt_version", "2.46")
    }

    dependencies{
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.46")
    }

    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.5.3" apply false
}