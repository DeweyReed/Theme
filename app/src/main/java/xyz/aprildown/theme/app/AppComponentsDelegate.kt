package xyz.aprildown.theme.app

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.ThemeInflationDelegate
import xyz.aprildown.theme.app.showcase.ColorAttributeView

class AppComponentsDelegate : ThemeInflationDelegate {
    override fun createView(context: Context, name: String, attrs: AttributeSet?): View? {
        return when (name) {
            "xyz.aprildown.theme.app.showcase.ColorAttributeView" -> ColorAttributeView(
                context,
                attrs
            ).apply {
                context.withStyledAttributes(attrs, R.styleable.ColorAttributeView) {
                    matchThemeColor(
                        resources,
                        this,
                        R.styleable.ColorAttributeView_cav_fill_color
                    )?.let {
                        dotFillColor = it
                    }
                    matchThemeColor(
                        resources,
                        this,
                        R.styleable.ColorAttributeView_cav_stroke_color
                    )?.let {
                        dotStrokeColor = it
                    }
                }
            }
            else -> null
        }
    }

    private fun matchThemeColor(resources: Resources, typedArray: TypedArray, index: Int): Int? {
        val resourceId = typedArray.getResourceId(index, -1)
        return try {
            if (resourceId != -1) {
                Theme.get().run {
                    when (resources.getResourceEntryName(resourceId)) {
                        "colorPrimary" -> colorPrimary
                        "colorPrimaryVariant" -> colorPrimaryVariant
                        "colorOnPrimary" -> colorOnPrimary
                        "colorSecondary" -> colorSecondary
                        "colorSecondaryVariant" -> colorSecondaryVariant
                        "colorOnSecondary" -> colorOnSecondary
                        else -> null
                    }
                }
            } else {
                null
            }
        } catch (e: Resources.NotFoundException) {
            null
        }
    }
}
