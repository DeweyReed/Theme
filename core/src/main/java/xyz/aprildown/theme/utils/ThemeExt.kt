package xyz.aprildown.theme.utils

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.CheckResult
import xyz.aprildown.theme.internal.KEY_ATTRIBUTE

@CheckResult
internal fun Context.attrKey(@AttrRes attrId: Int): String {
    var name = resources.safeResourceName(attrId)
    if (!name.startsWith("android")) {
        name = name.substring(name.indexOf(':') + 1)
    }
    return attrKey(name)
}

@CheckResult
internal fun attrKey(name: String) = String.format(KEY_ATTRIBUTE, name)