package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatSeekBar
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

internal class ProgressBarTint : BaseTint<ProgressBar>(
    attrs = R.styleable.Theme_ProgressBar,
    defStyleAttr = android.R.attr.progressBarStyle,
    onTint = {
        // R.style.Widget_AppCompat_ProgressBar
        // R.style.Widget_AppCompat_ProgressBar_Horizontal
        val progressBar = view
        withColorOrResourceId(
            R.styleable.Theme_ProgressBar_android_indeterminateTint,
            applySolidColor = {
                progressBar.indeterminateTintList = it.toColorStateList()
            },
            applyDefault = {
                progressBar.indeterminateTintList = Theme.get().colorSecondary.toColorStateList()
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_ProgressBar_android_progressTint,
            applySolidColor = {
                progressBar.progressTintList = it.toColorStateList()
            },
            applyDefault = {
                progressBar.progressTintList = Theme.get().colorSecondary.toColorStateList()
            }
        )
    }
)

internal class SeekBarTint : BaseTint<AppCompatSeekBar>(
    attrs = R.styleable.Theme_SeekBar,
    defStyleAttr = R.attr.seekBarStyle,
    onTint = {
        // R.style.Widget_AppCompat_SeekBar
        // R.style.Widget_AppCompat_SeekBar_Discrete
        val seekBar = view
        val context = seekBar.context
        withColorOrResourceId(
            R.styleable.Theme_SeekBar_android_thumbTint,
            applySolidColor = {
                seekBar.thumbTintList = it.toColorStateList()
            },
            applyDefault = {
                // R.drawable.seekbar_thumb_material_anim
                seekBar.thumbTintList = ColorStateList(
                    arrayOf(
                        intArrayOf(-android.R.attr.state_enabled),
                        intArrayOf()
                    ),
                    intArrayOf(
                        // Even if I set colorControlNormal here, the disabled color is still
                        // a little dark than the original color. I can't find a way to fix it.
                        // This is why Slider is better.
                        context.themeColor(android.R.attr.colorControlNormal),
                        Theme.get().colorSecondary
                    )
                )
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_ProgressBar_android_progressTint,
            applySolidColor = {
                seekBar.progressTintList = it.toColorStateList()
            },
            applyDefault = {
                seekBar.progressTintList = Theme.get().colorSecondary.toColorStateList()
            }
        )
    }
)
