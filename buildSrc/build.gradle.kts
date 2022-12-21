@file:Suppress("UnstableApiUsage")
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    implementation("com.android.tools.build:gradle:7.1.3")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.20.0")
}

