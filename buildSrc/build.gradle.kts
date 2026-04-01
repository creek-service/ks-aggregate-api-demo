import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "21"
    }
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
        )
        showStandardStreams = false
    }
}

dependencies {
    implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:6.4.8")                // https://plugins.gradle.org/plugin/com.github.spotbugs
    implementation("com.diffplug.spotless:spotless-plugin-gradle:8.4.0")                   // https://plugins.gradle.org/plugin/com.diffplug.spotless
    implementation("gradle.plugin.org.kt3k.gradle.plugin:coveralls-gradle-plugin:2.12.2")   // https://plugins.gradle.org/plugin/com.github.kt3k.coveralls
    implementation("org.javamodularity:moduleplugin:1.8.15")                                // https://plugins.gradle.org/plugin/org.javamodularity.moduleplugin
    implementation("io.github.gradle-nexus:publish-plugin:2.0.0")                           // https://plugins.gradle.org/plugin/io.github.gradle-nexus.publish-plugin
    implementation("org.creekservice:creek-system-test-gradle-plugin:0.4.1")                // https://plugins.gradle.org/plugin/org.creekservice.system.test
}
