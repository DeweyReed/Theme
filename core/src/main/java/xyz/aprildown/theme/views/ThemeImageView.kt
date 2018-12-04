package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.colorForAttrName

internal class ThemeImageView(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    init {
        val wizard = AttrWizard(context, attrs)
        val backgroundColorValue = wizard.getRawValue(android.R.attr.background)
        val tintColorValue = wizard.getRawValue(R.attr.tint)

        val theme = get(context)

        theme.colorForAttrName(backgroundColorValue)?.let {
            setBackgroundColor(it)
        }

        theme.colorForAttrName(tintColorValue)?.let {
            setColorFilter(it)
        }
    }
}