package com.ugisozols.biometricnoteapp.util

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.UriMatcher
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.ugisozols.biometricnoteapp.util.Constants.FINGERPRINT_DIALOG_SUBTITLE
import com.ugisozols.biometricnoteapp.util.Constants.FINGERPRINT_DIALOG_TITLE
import java.util.concurrent.Executor

@RequiresApi(Build.VERSION_CODES.R)
fun launchBiometricFingerprintReader(
    cancelationSignal: () -> CancellationSignal,
    authenticationSucceeded: () -> Unit,
    authenticationError: () -> Unit,
    context: Context
) {
    if (hasBiometricSupport(context)) {
        val biometricPrompt = BiometricPrompt.Builder(context)
            .setTitle(FINGERPRINT_DIALOG_TITLE)
            .setSubtitle(FINGERPRINT_DIALOG_SUBTITLE)
            .setConfirmationRequired(false)
            .setNegativeButton("", context.mainExecutor, { _, _ ->

            })
            .build()
        biometricPrompt.authenticate(
            cancelationSignal(),
            context.mainExecutor,
            authenticationCallback(authenticationSucceeded, authenticationError)
        )
    }
}

private fun hasBiometricSupport(context: Context): Boolean {
    val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    if (!keyguardManager.isDeviceSecure) {
        return true
    }
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.USE_BIOMETRIC
        ) == PackageManager.PERMISSION_DENIED
    ) {
        return false
    }
    return context.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
}


private fun authenticationCallback(
    authenticationSucceeded: () -> Unit,
    authenticationError: () -> Unit,

    ): BiometricPrompt.AuthenticationCallback =
    @RequiresApi(Build.VERSION_CODES.P)
    object : BiometricPrompt.AuthenticationCallback() {

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
            super.onAuthenticationError(errorCode, errString)
            authenticationError()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
            super.onAuthenticationSucceeded(result)
            authenticationSucceeded()
        }

    }