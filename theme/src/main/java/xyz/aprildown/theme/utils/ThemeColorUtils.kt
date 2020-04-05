/*
 * Copyright (C) 2018 Jared Rummler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.aprildown.theme.utils

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import kotlin.math.roundToInt
import androidx.core.graphics.ColorUtils as AndroidColorUtils

@ColorInt
internal fun Int.darker(@FloatRange(from = 0.0, to = 1.0) factor: Float = 0.85f): Int {
    return Color.argb(
        Color.alpha(this), (Color.red(this) * factor).toInt().coerceAtLeast(0),
        (Color.green(this) * factor).toInt().coerceAtLeast(0),
        (Color.blue(this) * factor).toInt().coerceAtLeast(0)
    )
}

@ColorInt
internal fun Int.lighter(@FloatRange(from = 0.0, to = 1.0) factor: Float = 0.15f): Int {
    val alpha = Color.alpha(this)
    val red = ((Color.red(this) * (1 - factor) / 255 + factor) * 255).toInt()
    val green = ((Color.green(this) * (1 - factor) / 255 + factor) * 255).toInt()
    val blue = ((Color.blue(this) * (1 - factor) / 255 + factor) * 255).toInt()
    return Color.argb(alpha, red, green, blue)
}

// internal val Int.isDarkColor: Boolean get() = this.isDarkColor(0.5)

// internal fun Int.isDarkColor(@FloatRange(from = 0.0, to = 1.0) luminance: Double): Boolean {
//     return AndroidColorUtils.calculateLuminance(this) <= luminance
// }

internal val Int.isLightColor: Boolean get() = this.isLightColor(0.5)

internal fun Int.isLightColor(@FloatRange(from = 0.0, to = 1.0) luminance: Double = 0.5): Boolean {
    return AndroidColorUtils.calculateLuminance(this) >= luminance
}

@ColorInt
internal fun Int.adjustAlpha(@FloatRange(from = 0.0, to = 1.0) factor: Float): Int {
    val alpha = (Color.alpha(this) * factor).roundToInt()
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}

@ColorInt
internal fun Int.stripAlpha(): Int = Color.rgb(Color.red(this), Color.green(this), Color.blue(this))

// @ColorInt
// internal fun Int.shiftColor(@FloatRange(from = 0.0, to = 2.0) by: Float): Int {
//     if (by == 1f) return this
//     val hsv = FloatArray(3)
//     Color.colorToHSV(this, hsv)
//     hsv[2] *= by // value component
//     return Color.HSVToColor(hsv)
// }

internal fun Int.toColorStateList(): ColorStateList = ColorStateList.valueOf(this)
