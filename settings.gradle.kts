pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven {
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
}

rootProject.name = "ks-aggregate-api-demo"

include(
    "api",
    "handle-occurrence-service",
    "services",
    "system-tests"
)
