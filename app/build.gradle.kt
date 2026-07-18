plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt") // Для Room annotations processing
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true // Включение View Binding
    }
}

dependencies {
    // === Android Core ===
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // === Lifecycle & ViewModel (для реактивного UI) ===
    val lifecycleVersion = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    // === Room Database (ORM) ===
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    
    // Annotation processing для Room
    kapt("androidx.room:room-compiler:$roomVersion")
    
    // Для Kotlin Coroutines с Room (альтернатива к apt)
    // kapt("androidx.room:room-ktx:$roomVersion")

    // === Coroutines ===
    val coroutinesVersion = "1.7.3"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // === Navigation (для навигации между экранами) ===
    val navigationVersion = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // === Lifecycle Process (для сохранения состояния при background) ===
    implementation("androidx.lifecycle:lifecycle-process:$lifecycleVersion")

    // === Testing ===
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    
    // Для тестирования Room
    testImplementation("androidx.room:room-testing:$roomVersion")

    // === Optional: Glide для изображений (если нужно) ===
    // implementation("com.github.bumptech.glide:glide:4.16.0")
}

// === Kotlin Extensions for Flow ===
kotlin {
    sourceSets {
        getByName("main").apply {
            kotlin.srcDirs("src/main/java/com/example/noteapp/Extensions.kt")
        }
    }
}