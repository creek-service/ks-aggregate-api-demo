plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

val jvmTargetVer = JavaLanguageVersion.of(11)

java {
    toolchain.languageVersion.set(jvmTargetVer)
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(jvmTargetVer)
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "$jvmTargetVer"
    }
}

dependencies {
    implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:6.0.7")                // https://plugins.gradle.org/plugin/com.github.spotbugs
    implementation("com.diffplug.spotless:spotless-plugin-gradle:7.2.1")                   // https://plugins.gradle.org/plugin/com.diffplug.spotless
    implementation("gradle.plugin.org.kt3k.gradle.plugin:coveralls-gradle-plugin:2.12.2")   // https://plugins.gradle.org/plugin/com.github.kt3k.coveralls
    implementation("org.javamodularity:moduleplugin:1.8.12")                                // https://plugins.gradle.org/plugin/org.javamodularity.moduleplugin
    implementation("com.gradle.publish:plugin-publish-plugin:1.2.1")                        // https://plugins.gradle.org/plugin/com.gradle.plugin-publish
    implementation("org.creekservice:creek-system-test-gradle-plugin:0.4.1")                // https://plugins.gradle.org/plugin/org.creekservice.system.test
}