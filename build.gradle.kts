import org.jetbrains.kotlin.konan.properties.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

// Read version from properties file
val localProperties = Properties()
localProperties.load(java.io.File("local.properties").reader())

val appVersion: String? = localProperties.getProperty("app.version") ?: "1.0"