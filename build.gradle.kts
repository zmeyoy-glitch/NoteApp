import org.jetbrains.kotlin.konan.properties.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

val localProperties = Properties()
localProperties.load(java.io.FileInputStream("local.properties").bufferedReader())

// Read version from properties file or use default values
val appVersionName: String? = localProperties.getProperty("app.version.name") ?: "1.0.0"
val appVersionCode: Int = localProperties.getProperty("app.version.code", "1").toInt()

println("Building NoteApp v$appVersionName (code $appVersionCode)")