package plugins

import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:1.20.0")
}

tasks.register<Detekt>("detektAll") {
    description = "Runs Detekt on the whole project at once."
    parallel = true
    setSource(projectDir)
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    exclude("**/buildSrc/**")
    exclude("**/build.gradle.kts")
    config.setFrom(files("$rootDir/tools/detekt/detekt.yml"))
}
