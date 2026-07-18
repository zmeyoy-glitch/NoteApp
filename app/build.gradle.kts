plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt") // для room compiler
}

android {
    namespace = "com.example.noteapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.noteapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testOptions {
            initializer block =::initKotlin
        }

        lintOptions {
            disableWarnings("MissingTranslation")
        }
    }

    buildTypes {
        release = buildType()
        debug = buildType()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions,
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Room для базы данных заметок
    implementation("androidx.room:room-runtime:2.6.1")
    kapt(" androidx.room:room-compiler:2.6.1")
    implementation(" androidx.room:room-ktx:2.6.1") // расширения корутин

    // Jetpack Compose для UI
    implementation(" androidx.compose.ui:ui:1.5.4")
    implementation(" androidx.compose.material3:material3:1.1.2")
}
