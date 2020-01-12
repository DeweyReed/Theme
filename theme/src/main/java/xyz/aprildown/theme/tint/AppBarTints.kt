package xyz.aprildown.theme.tint

import com.google.android.material.bottomappbar.BottomAppBar
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.toColorStateList

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/BottomAppBar.md
 */
internal class BottomAppBarTint : BaseTint<BottomAppBar>(
    attrs = R.styleable.Theme_BottomAppBar,
    defStyleAttr = R.attr.bottomAppBarStyle,
    onTint = {
        val bottomAppBar = view
        findThemeColor(R.styleable.Theme_BottomAppBar_backgroundTint)?.let {
            bottomAppBar.backgroundTint = it.toColorStateList()
        }
    }
)
