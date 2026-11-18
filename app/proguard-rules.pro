# Proguard rules for ComeBack / NutriPortfolio

# Credential Manager / Google ID
-keep class com.google.android.libraries.identity.googleid.** { *; }
-keep class androidx.credentials.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Datastore
-keepclassmembers class * extends androidx.datastore.preferences.core.Preferences { *; }
