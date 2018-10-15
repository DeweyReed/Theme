package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.changeBackgroundColor
import xyz.aprildown.theme.utils.changeImageTint
import xyz.aprildown.theme.utils.colorForAttrName

internal class ThemeImageView(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private val wizard = AttrWizard(context, attrs)
    private val backgroundColorValue = wizard.getRawValue(android.R.attr.background)
    private val tintColorValue = wizard.getRawValue(R.attr.tint)

    init {
        get().colorForAttrName(backgroundColorValue)?.let {
            changeBackgroundColor(it)
        }

        get().colorForAttrName(tintColorValue)?.let {
            changeImageTint(it)
        }
    }
}