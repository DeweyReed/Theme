package xyz.aprildown.theme.utils

import android.os.Build

internal fun isMOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

internal fun isNOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

// internal fun isNMR1OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1

internal fun isOOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

internal fun isPOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

internal fun isQOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
