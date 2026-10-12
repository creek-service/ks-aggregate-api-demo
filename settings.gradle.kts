// TODO(0.5.1 release): Remove this temporary pluginManagement closure
pluginManagement {
    repositories {
        gradlePluginPortal()
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
