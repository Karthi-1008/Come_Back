# ComeBack (My Daily Portfolio) - Stage 1 Foundation

Android open-source daily nutrition and health portfolio app (offline food DB, Google sign-in, onboarding survey). Built with Kotlin and Jetpack Compose.

## Prerequisites

1. **Android Studio** (current stable version) with Android SDK Platform 35 and **JDK 17**.
2. **Google Cloud Project Setup**:
   - Create an OAuth 2.0 Web Application client ID and an Android client ID (matching package `com.example.nutriportfolio` and your debug/release SHA-1 keystore fingerprints).
   - In `local.properties` in the project root, add:
     ```properties
     WEB_CLIENT_ID=<your-web-client-id>
     ```
     *(Note: If `WEB_CLIENT_ID` is left empty, the app will still build and run; clicking Google Sign-in will calmly inform you that sign-in is not yet configured.)*
3. **Test Device/Emulator**:
   - Android 8.0 (API 26) or above with Google Play services (required for Google sign-in).

## Build Instructions

- **Run unit tests:**
  ```bash
  ./gradlew test
  ```
- **Build Debug APK:**
  ```bash
  ./gradlew assembleDebug
  ```
- **Build Release APK:**
  ```bash
  ./gradlew assembleRelease
  ```

## Languages Supported
- English (default)
- தமிழ் (Tamil)
- తెలుగు (Telugu)
- മലയാളം (Malayalam)
- हिन्दी (Hindi)
