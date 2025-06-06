// Top-level build file where you can add configuration options common to all sub-projects/modules.


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
//    kapt { javacOptions { option("-J--add-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED") } }
}

buildscript {
    dependencies {
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
    }
}