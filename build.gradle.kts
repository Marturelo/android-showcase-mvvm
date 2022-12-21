
plugins {
    id("io.gitlab.arturbosch.detekt")
}

buildscript {

    val kotlinVersion by extra { "1.5.31" }
    val navVersion by extra { "2.4.1" }
    val androidGradlePluginVersion by extra { "7.3.0" }
    val jacocoVersion by extra { "0.8.7" }
    val sonarqubeVersion by extra { "2.8" }
    val detektVersion by extra { "1.20.0" }

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
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion")
    }
}

//apply(from = "io.gitlab.arturbosch.detekt")

tasks.register("detektAll", io.gitlab.arturbosch.detekt.Detekt::class.java) {
    parallel = true
    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    exclude("**/buildSrc/**")
    exclude("**/build.gradle.kts")
    config.setFrom(files("$rootDir/tools/detekt/detekt.yml"))
    buildUponDefaultConfig = false
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

copy {
    from("$rootDir/tools/git_scripts/pre-commit")
    into("$rootDir/.git/hooks")
    fileMode = 0b000_111_111_111
}