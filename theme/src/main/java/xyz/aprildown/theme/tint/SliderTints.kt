@file:Suppress("FunctionName")

package xyz.aprildown.theme.tint

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/Slider.md
 * [R.style.Widget_MaterialComponents_Slider]
 * [R.style.Base_Widget_MaterialComponents_Slider]
 */
internal class SliderTint : BaseTint<Slider>(
    attrs = R.styleable.Theme_Slider,
    defStyleAttr = R.attr.sliderStyle,
    onTint = {
        val slider = view
        val context = slider.context
        withColorOrResourceId(
            R.styleable.Theme_Slider_haloColor,
            applySolidColor = {
                slider.haloTintList = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.material_slider_halo_color -> {
                        slider.haloTintList = material_slider_halo_color()
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_Slider_thumbColor,
            applySolidColor = {
                slider.thumbTintList = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.material_slider_thumb_color -> {
                        slider.thumbTintList = material_slider_thumb_color(context)
                    }
                }
            }
        )
        matchThemeColor(R.styleable.Theme_Slider_thumbStrokeColor)?.let {
            slider.thumbStrokeColor = it.toColorStateList()
        }
        if (typedArray.hasValue(R.styleable.Theme_Slider_tickColor)) {
            withColorOrResourceId(
                R.styleable.Theme_Slider_tickColor,
                applySolidColor = {
                    val csl = it.toColorStateList()
                    slider.tickActiveTintList = csl
                    slider.tickInactiveTintList = csl
                }
            )
        } else {
            withColorOrResourceId(
                R.styleable.Theme_Slider_tickColorActive,
                applySolidColor = {
                    slider.tickActiveTintList = it.toColorStateList()
                },
                applyResource = {
                    when (it) {
                        R.color.material_slider_active_tick_marks_color -> {
                            slider.tickActiveTintList = material_slider_active_tick_marks_color()
                        }
                    }
                }
            )
            withColorOrResourceId(
                R.styleable.Theme_Slider_tickColorInactive,
                applySolidColor = {
                    slider.tickInactiveTintList = it.toColorStateList()
                },
                applyResource = {
                    when (it) {
                        R.color.material_slider_inactive_tick_marks_color -> {
                            slider.tickInactiveTintList =
                                material_slider_inactive_tick_marks_color(context)
                        }
                    }
                }
            )
        }
        if (typedArray.hasValue(R.styleable.Theme_Slider_trackColor)) {
            withColorOrResourceId(
                R.styleable.Theme_Slider_trackColor,
                applySolidColor = {
                    val csl = it.toColorStateList()
                    slider.trackActiveTintList = csl
                    slider.trackInactiveTintList = csl
                }
            )
        } else {
            withColorOrResourceId(
                R.styleable.Theme_Slider_trackColorActive,
                applySolidColor = {
                    slider.trackActiveTintList = it.toColorStateList()
                },
                applyResource = {
                    when (it) {
                        R.color.material_slider_active_track_color -> {
                            slider.trackActiveTintList = material_slider_active_track_color(slider)
                        }
                    }
                }
            )
            withColorOrResourceId(
                R.styleable.Theme_Slider_trackColorInactive,
                applySolidColor = {
                    slider.trackInactiveTintList = it.toColorStateList()
                },
                applyResource = {
                    when (it) {
                        R.color.material_slider_inactive_track_color -> {
                            slider.trackInactiveTintList =
                                material_slider_inactive_track_color(context)
                        }
                    }
                }
            )
        }
    }
)

/** [R.color.material_slider_halo_color] */
private fun material_slider_halo_color(): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary.adjustAlpha(0.24f),
            Color.TRANSPARENT
        )
    )
}

/** [R.color.material_slider_thumb_color] */
private fun material_slider_thumb_color(context: Context): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary,
            context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.38f)
        )
    )
}

/** [R.color.material_slider_active_tick_marks_color] */
private fun material_slider_active_tick_marks_color(): ColorStateList {
    val colorOnPrimary = Theme.get().colorOnPrimary
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            colorOnPrimary.adjustAlpha(0.54f),
            colorOnPrimary.adjustAlpha(0.12f)
        )
    )
}

/** [R.color.material_slider_inactive_tick_marks_color] */
private fun material_slider_inactive_tick_marks_color(context: Context): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorOnPrimary.adjustAlpha(0.54f),
            context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.12f)
        )
    )
}

/** [R.color.material_slider_active_track_color] */
private fun material_slider_active_track_color(view: View): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary,
            view.context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.32f)
        )
    )
}

/** [R.color.material_slider_inactive_track_color] */
private fun material_slider_inactive_track_color(context: Context): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf()
        ),
        intArrayOf(
            Theme.get().colorPrimary.adjustAlpha(0.24f),
            context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.12f)
        )
    )
}

/**
 * Copy and paste from [SliderTint] because BaseSlider isn't open.
 */
internal class RangeSliderTint : BaseTint<RangeSlider>(
    attrs = R.styleable.Theme_Slider,
    defStyleAttr = R.attr.sliderStyle,
    onTint = {
        val slider = view
        val context = slider.context
        withColorOrResourceId(
            R.styleable.Theme_Slider_haloColor,
            applySolidColor = {
                slider.haloTintList = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.material_slider_halo_color -> {
                        slider.haloTintList = material_slider_halo_color()
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_Slider_thumbColor,
            applySolidColor = {
                slider.thumbTintList = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.material_slider_thumb_color -> {
                        slider.thumbTintList = material_slider_thumb_color(context)
                    }
                }
            }
        )
        matchThemeColor(R.styleable.Theme_Slider_thumbStrokeColor)?.let {
            slider.thumbStrokeColor = it.toColorStateList()
        }
        if (typedArray.hasValue(R.styleable.Theme_Slider_tickColor)) {
            withColorOrResourceId(
                R.styleable.Theme_Slider_tickColor,
                applySolidColor = {
                    val csl = it.toColorStateList()
                    slider.tickActiveTintList = csl
                    slider.tickInactiveTintList = csl
                }
            )
        } else {
            withColorOrResourceId(
                R.styleable.Theme_Slider_tickColorActive,
                applySolidColor = {
                    slider.tickActiveTintList = it.toColorStateList()
                },
                applyResource = {
                    when (it) {
                        R.color.material_slider_active_tick_marks_color -> {
                            slider.tickActiveTintList = material_slider_active_tick_marks_color()
                        }
                    }
                }
            )
            withColorOrResourceId(
                R.styleable.Theme_Slider_tickColorInactive,
                applySolidColor = {
                    slider.tickInactiveTintList = it.toColorStateList()
                },
                applyResource = {
                    when (it) {
                        R.color.material_slider_inactive_tick_marks_color -> {
                            slider.tickInactiveTintList =
                                material_slider_inactive_tick_marks_color(context)
                        }
                    }
                }
            )
        }
        if (typedArray.hasValue(R.styleable.Theme_Slider_trackColor)) {
            withColorOrResourceId(
                R.styleable.Theme_Slider_trackColor,
                applySolidColor = {
                    val csl = it.toColorStateList()
                    slider.trackActiveTintList = csl
                    slider.trackInactiveTintList = csl
                }
            )
        } else {
            withColorOrResourceId(
                R.styleable.Theme_Slider_trackColorActive,
                applySolidColor = {
                    slider.trackActiveTintList = it.toColorStateList()
                },
                applyResource = {
                    when (it) {
                        R.color.material_slider_active_track_color -> {
                            slider.trackActiveTintList = material_slider_active_track_color(slider)
                        }
                    }
                }
            )
            withColorOrResourceId(
                R.styleable.Theme_Slider_trackColorInactive,
                applySolidColor = {
                    slider.trackInactiveTintList = it.toColorStateList()
                },
                applyResource = {
                    when (it) {
                        R.color.material_slider_inactive_track_color -> {
                            slider.trackInactiveTintList =
                                material_slider_inactive_track_color(context)
                        }
                    }
                }
            )
        }
    }
)
