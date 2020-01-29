package xyz.aprildown.theme.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager

private val Context.safeContext: Context
    get() = takeIf { Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !isDeviceProtectedStorage }?.let {
        ContextCompat.createDeviceProtectedStorageContext(it) ?: it
    } ?: this

val Context.safeSharedPreference: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(safeContext)
