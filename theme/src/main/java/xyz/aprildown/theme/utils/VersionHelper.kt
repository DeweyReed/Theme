package xyz.aprildown.theme.utils

import android.os.Build

internal fun isNOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

internal fun isOOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

internal fun isPOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

internal fun isQOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
