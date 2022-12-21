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
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")
    config.setFrom(project.file("detekt/config.yml"))
}