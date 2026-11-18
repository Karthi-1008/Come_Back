package com.example.nutriportfolio.data.repo

import android.app.Activity
import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialProviderConfigurationException
import androidx.credentials.exceptions.NoCredentialException
import com.example.nutriportfolio.BuildConfig
import com.example.nutriportfolio.R
import com.example.nutriportfolio.data.prefs.SettingsStore
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

sealed interface AuthResult {
    data class Success(val displayName: String?, val email: String?) : AuthResult
    data class Error(val messageResId: Int) : AuthResult
}

interface AuthRepository {
    val isSignedInFlow: Flow<Boolean>
    val userNameFlow: Flow<String?>
    val userEmailFlow: Flow<String?>
    suspend fun signIn(activity: Activity): AuthResult
    suspend fun signOut(context: Context)
}

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val credentialManager: CredentialManager,
    private val settingsStore: SettingsStore
) : AuthRepository {

    override val isSignedInFlow: Flow<Boolean> = settingsStore.isSignedInFlow
    override val userNameFlow: Flow<String?> = settingsStore.userNameFlow
    override val userEmailFlow: Flow<String?> = settingsStore.userEmailFlow

    override suspend fun signIn(activity: Activity): AuthResult {
        val serverClientId = BuildConfig.WEB_CLIENT_ID
        if (serverClientId.isBlank()) {
            return AuthResult.Error(R.string.err_not_configured)
        }

        return try {
            val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(serverClientId)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(signInWithGoogleOption)
                .build()

            val response = credentialManager.getCredential(activity, request)
            val credential = response.credential

            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val displayName = googleIdTokenCredential.displayName
                val email = googleIdTokenCredential.id

                settingsStore.setSignedIn(true, displayName, email)
                AuthResult.Success(displayName = displayName, email = email)
            } else {
                AuthResult.Error(R.string.err_generic)
            }
        } catch (_: GetCredentialCancellationException) {
            AuthResult.Error(R.string.err_cancelled)
        } catch (_: NoCredentialException) {
            AuthResult.Error(R.string.err_no_account)
        } catch (_: GetCredentialProviderConfigurationException) {
            AuthResult.Error(R.string.err_play_services)
        } catch (_: UnknownHostException) {
            AuthResult.Error(R.string.err_no_internet)
        } catch (_: IOException) {
            AuthResult.Error(R.string.err_no_internet)
        } catch (e: Exception) {
            val message = e.message?.lowercase() ?: ""
            if (message.contains("network") || message.contains("connection") || message.contains("offline")) {
                AuthResult.Error(R.string.err_no_internet)
            } else if (message.contains("play services") || message.contains("api_not_connected")) {
                AuthResult.Error(R.string.err_play_services)
            } else {
                AuthResult.Error(R.string.err_generic)
            }
        }
    }

    override suspend fun signOut(context: Context) {
        try {
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
        } catch (_: Exception) {
            // Ignore failure on clearing remote credential state if offline
        }
        settingsStore.clearAccount()
    }
}
