package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.view.View
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.float
import xyz.aprildown.theme.utils.toColorStateList

/**
 * [R.style.Widget_MaterialComponents_FloatingActionButton]
 * https://github.com/material-components/material-components-android/blob/master/docs/components/FloatingActionButton.md
 */
internal class FloatingActionButtonTint : BaseTint<FloatingActionButton>(
    attrs = R.styleable.Theme_FloatingActionButton,
    defStyleAttr = R.attr.floatingActionButtonStyle,
    onTint = {
        val fab = view
        matchThemeColor(R.styleable.Theme_FloatingActionButton_backgroundTint)?.let {
            ViewCompat.setBackgroundTintList(fab, it.toColorStateList())
        }
        matchThemeColor(R.styleable.Theme_FloatingActionButton_tint)?.let {
            fab.imageTintList = it.toColorStateList()
        }
        withColorOrResourceId(
            R.styleable.Theme_FloatingActionButton_rippleColor,
            applySolidColor = {
                fab.rippleColor = it
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_fab_ripple_color -> {
                        fab.setRippleColor(fab.mtrl_fab_ripple_color())
                    }
                }
            }
        )
    }
)

// R.color.mtrl_fab_ripple_color
private fun View.mtrl_fab_ripple_color(): ColorStateList {
    val context = context
    val colorOnSecondary = Theme.get().colorOnSecondary
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf()
        ),
        intArrayOf(
            colorOnSecondary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_pressed_alpha)),
            colorOnSecondary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_focused_alpha)),
            colorOnSecondary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_hovered_alpha)),
            colorOnSecondary.adjustAlpha(context.float(R.dimen.mtrl_high_ripple_default_alpha))
        )
    )
}
