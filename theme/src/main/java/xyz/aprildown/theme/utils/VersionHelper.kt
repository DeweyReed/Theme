package xyz.aprildown.theme.utils

import android.os.Build

fun isMOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

// fun isNOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

// fun isNMR1OrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1

fun isOOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

fun isPOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

fun isQOrLater(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
