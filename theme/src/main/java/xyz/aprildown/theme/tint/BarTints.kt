package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.widget.ProgressBar
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatSeekBar
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec
import com.google.android.material.progressindicator.LinearProgressIndicator
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.themeFloat
import xyz.aprildown.theme.utils.toColorStateList

/**
 * [R.style.Widget_AppCompat_ProgressBar]
 * [R.style.Widget_AppCompat_ProgressBar_Horizontal]
 */
internal class ProgressBarTint : BaseTint<ProgressBar>(
    attrs = R.styleable.Theme_ProgressBar,
    defStyleAttr = android.R.attr.progressBarStyle,
    onTint = {
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

/**
 * [R.style.Widget_AppCompat_SeekBar]
 * [R.style.Widget_AppCompat_SeekBar_Discrete]
 */
internal class SeekBarTint : BaseTint<AppCompatSeekBar>(
    attrs = R.styleable.Theme_SeekBar,
    defStyleAttr = R.attr.seekBarStyle,
    onTint = {
        val seekBar = view
        val context = seekBar.context
        withColorOrResourceId(
            R.styleable.Theme_SeekBar_android_thumbTint,
            applySolidColor = {
                seekBar.thumbTintList = it.toColorStateList()
            },
            applyDefault = {
                /** [android.R.drawable.seekbar_thumb_material_anim] */
                seekBar.thumbTintList = ColorStateList(
                    arrayOf(
                        intArrayOf(-android.R.attr.state_enabled),
                        intArrayOf()
                    ),
                    intArrayOf(
                        // Even if I set colorControlNormal here, the disabled color is still
                        // a little darker than the original color. I can't find a way to fix it.
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

/**
 * [R.style.Widget_MaterialComponents_LinearProgressIndicator]
 * [R.style.Widget_MaterialComponents_CircularProgressIndicator]

 * [BaseProgressIndicatorSpec.loadIndicatorColors]
 * [BaseProgressIndicatorSpec.loadTrackColor]
 */
internal abstract class ProgressIndicatorTint(
    @AttrRes defStyleAttr: Int
) : BaseTint<BaseProgressIndicator<*>>(
    attrs = R.styleable.Theme_ProgressIndicator,
    defStyleAttr = defStyleAttr,
    onTint = {
        val progress = view
        val context = view.context

        val indicatorColor = matchThemeColor(R.styleable.Theme_ProgressIndicator_indicatorColor)
            ?: Theme.get().colorPrimary

        val trackColor = matchThemeColor(R.styleable.Theme_ProgressIndicator_trackColor)
        progress.setIndicatorColor(indicatorColor)

        if (trackColor != null) {
            progress.trackColor = trackColor
        } else if (progress is LinearProgressIndicator &&
            !typedArray.hasValue(R.styleable.Theme_ProgressIndicator_trackColor)
        ) {
            var alpha = context.themeFloat(android.R.attr.disabledAlpha)
            if (alpha == 0f) {
                alpha = 0.2f
            }
            progress.trackColor = indicatorColor.adjustAlpha(alpha)
        }
    }
)

internal class LinearProgressIndicatorTint :
    ProgressIndicatorTint(R.attr.linearProgressIndicatorStyle)

internal class CircularProgressIndicatorTint :
    ProgressIndicatorTint(R.attr.circularProgressIndicatorStyle)
