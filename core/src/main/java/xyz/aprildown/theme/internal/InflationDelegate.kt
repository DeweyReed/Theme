package xyz.aprildown.theme.internal

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IdRes

interface InflationDelegate {
    fun createView(
        context: Context,
        attrs: AttributeSet?,
        name: String,
        @IdRes viewId: Int
    ): View?
}
