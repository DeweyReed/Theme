package xyz.aprildown.theme

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.StyleableRes

/**
 * The delegate can't hijack those methods which are started with "create" in [ThemeViewInflater].
 * This class is designed to handle custom views.
 */
abstract class ThemeInflationDelegate {

    /**
     * @return Return null if we decide to leave the creation job to Android.
     */
    abstract fun createView(
        context: Context,
        name: String,
        attrs: AttributeSet?
    ): View?

    /**
     * A helper method to match Theme colors. For example,
     * if (name == "package.MyCustomView") {
     *     MyCustomView(context, attrs).apply {
     *         context.withStyledAttributes(attrs, R.styleable.MyCustomView) {
     *             matchThemeColor(this, R.styleable.MyCustomView_mcv_color)?.let {
     *                 setMyCustomViewColor(it)
     *             }
     *         }
     *     }
     * }
     */
    fun matchThemeColor(typedArray: TypedArray, @StyleableRes index: Int): Int? {
        return typedArray.matchThemeColor(index)
    }
}
