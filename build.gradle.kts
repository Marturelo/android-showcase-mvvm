buildscript {

    val kotlinVersion by extra { "1.5.31" }
    val navVersion by extra { "2.4.1" }
    val androidGradlePluginVersion by extra { "7.0.3" }
    val jacocoVersion by extra { "0.8.7" }
    val sonarqubeVersion by extra { "2.8" }

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${androidGradlePluginVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        classpath("org.jacoco:org.jacoco.core:$jacocoVersion")
        classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:$sonarqubeVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}