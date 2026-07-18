import org.jetbrains.kotlin.konan.properties.Properties

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NoteApp"
include(":app")

// Read version from properties file
val localProperties = Properties()
localProperties.load(java.io.File("local.properties").reader())

val appVersion: String? = localProperties.getProperty("app.version") ?: "1.0"