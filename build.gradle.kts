import org.jetbrains.kotlin.kotlinx.coroutines.core.MainDispatcher

plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("androidx.room") version "2.6.1" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
        classpath("androidx.room:room-ktx:2.6.1")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
    
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

subprojects {
    apply(plugin = "com.android.application")
    apply(plugin = "org.jetbrains.kotlin.android")
    
    android {
        namespace = "com.example.noteapp"
        
        compileSdk = 34
        
        defaultConfig {
            applicationId = "com.example.noteapp"
            minSdk = 24
            targetSdk = 34
            versionCode = 1
            versionName = "1.0"
            
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    
    dependencies {
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.10.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        
        // Room Database
        implementation("androidx.room:room-runtime:2.6.1")
        kapt("androidx.room:room-compiler:2.6.1")
        implementation("androidx.room:room-ktx:2.6.1")
    }
}

rootProject.name = "NoteApp"
include(":app")