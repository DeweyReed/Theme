package xyz.aprildown.theme.tint

import android.view.ViewGroup
import androidx.core.view.forEach
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import xyz.aprildown.theme.R
import xyz.aprildown.theme.utils.setMaterialBackgroundColor

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/AppBarLayout.md
 */
internal class AppBarLayoutTint : BaseTint<AppBarLayout>(
    attrs = R.styleable.Theme_AppBarLayout,
    defStyleAttr = R.attr.appBarLayoutStyle,
    onTint = {
        // R.style.Widget_MaterialComponents_AppBarLayout_Surface
        // R.style.Widget_MaterialComponents_AppBarLayout_Primary
        val appBarLayout = view
        matchThemeColor(R.styleable.Theme_AppBarLayout_android_background)?.let {
            appBarLayout.setMaterialBackgroundColor(it)
        }
    }
)

internal class CollapsingToolbarLayoutTint : BaseTint<CollapsingToolbarLayout>(
    attrs = R.styleable.Theme_CollapsingToolbarLayout,
    onTint = {
        // R.style.Widget_Design_CollapsingToolbar
        val ctl = view
        matchThemeColor(R.styleable.Theme_CollapsingToolbarLayout_contentScrim)?.let {
            ctl.setContentScrimColor(it)
        }
        matchThemeColor(R.styleable.Theme_CollapsingToolbarLayout_statusBarScrim)?.let {
            ctl.setStatusBarScrimColor(it)
        }

        fun ViewGroup.findToolbar(): ThemeToolbar? {
            forEach { child ->
                when (child) {
                    is ThemeToolbar -> return child
                    is ViewGroup -> return child.findToolbar()
                }
            }
            return null
        }

        // This is hack and ignores your setters in the activity's onCreate.
        withColorOrResourceId(
            R.styleable.Theme_CollapsingToolbarLayout_expandedTitleTextAppearance,
            applyDefault = {
                // Post because AppBarLayout isn't available now.
                ctl.post {
                    ctl.findToolbar()?.colorOnToolbar?.let {
                        ctl.setExpandedTitleTextColor(it)
                    }
                }
            }
        )
        withColorOrResourceId(
            R.styleable.Theme_CollapsingToolbarLayout_collapsedTitleTextAppearance,
            applyDefault = {
                ctl.post {
                    ctl.findToolbar()?.colorOnToolbar?.let {
                        ctl.setCollapsedTitleTextColor(it)
                    }
                }
            }
        )
    }
)
