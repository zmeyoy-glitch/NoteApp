# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in your build.gradle.kts file.

# Keep Room entities and DAOs
-keep class com.example.noteapp.entity.** { *; }
-keep class com.example.noteapp.dao.** { *; }

# Keep annotations for Room
-keepattributes Annotation
-keepattributes EnclosingMethod

# Keep Kotlin metadata
-keepattributes InnerClasses
-keepattributes Signature