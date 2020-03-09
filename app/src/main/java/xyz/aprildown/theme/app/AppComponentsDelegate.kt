package xyz.aprildown.theme.app

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import xyz.aprildown.theme.ThemeInflationDelegate
import xyz.aprildown.theme.app.showcase.ColorAttributeView

class AppComponentsDelegate : ThemeInflationDelegate() {
    override fun createView(context: Context, name: String, attrs: AttributeSet?): View? {
        return when (name) {
            "xyz.aprildown.theme.app.showcase.ColorAttributeView" ->
                ColorAttributeView(context, attrs).apply {
                    context.withStyledAttributes(attrs, R.styleable.ColorAttributeView) {
                        matchThemeColor(
                            this,
                            R.styleable.ColorAttributeView_cav_fill_color
                        )?.let {
                            dotFillColor = it
                        }
                        matchThemeColor(
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
}
