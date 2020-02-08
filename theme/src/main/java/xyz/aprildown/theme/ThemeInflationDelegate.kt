package xyz.aprildown.theme

import android.content.Context
import android.util.AttributeSet
import android.view.View

interface ThemeInflationDelegate {
    fun createView(
        context: Context,
        name: String,
        attrs: AttributeSet?
    ): View?
}
