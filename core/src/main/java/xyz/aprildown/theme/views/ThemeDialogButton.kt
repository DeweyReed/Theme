package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import xyz.aprildown.theme.Theme.Companion.get

internal class ThemeDialogButton(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

    init {
        setTextColor(get(context).colorAccent)
    }
}
