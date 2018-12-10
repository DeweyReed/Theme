@file:Suppress("SpellCheckingInspection")

package xyz.aprildown.theme.internal

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Switch
import androidx.annotation.IdRes
import androidx.appcompat.widget.*
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import xyz.aprildown.theme.InflationDelegate
import xyz.aprildown.theme.tint.decorate
import xyz.aprildown.theme.tint.decorateBorderlessButton
import xyz.aprildown.theme.tint.decorateDialogButton
import xyz.aprildown.theme.tint.decorateNormalButton
import xyz.aprildown.theme.utils.resId
import xyz.aprildown.theme.views.ThemeActionMenuItemView
import xyz.aprildown.theme.views.ThemeDrawerLayout
import xyz.aprildown.theme.views.ThemeToolbar

internal class InflationInterceptor(
    private val delegates: Array<out InflationDelegate>
) : LayoutInflater.Factory2 {

    override fun onCreateView(
        name: String?,
        context: Context?,
        attrs: AttributeSet?
    ): View {
        return onCreateView(name, context, attrs)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet?
    ): View? {
        val viewId = context.resId(attrs, android.R.attr.id)
        var view: View?
        // Custom Views First
        for (delegate in delegates) {
            view = delegate.createView(context, attrs, name, viewId)
            if (view != null) return view
        }
        view = name.viewForName(context, attrs, viewId)
        return view
    }

    private fun String.viewForName(
        context: Context,
        attrs: AttributeSet?,
        @IdRes viewId: Int
    ): View? {
        return when (this) {
            "ImageView", "$APPCOMPAT_WIDGET.AppCompatImageView" ->
                AppCompatImageView(context, attrs).decorate(attrs)
            "ImageButton", "$APPCOMPAT_WIDGET.AppCompatImageButton" ->
                AppCompatImageButton(context, attrs).decorate(attrs)

            "androidx.drawerlayout.widget.DrawerLayout" ->
                ThemeDrawerLayout(context, attrs)
            "Toolbar", "$APPCOMPAT_WIDGET.Toolbar" ->
                ThemeToolbar(context, attrs)

            "$APPCOMPAT_WIDGET.AppCompatTextView", "TextView" ->
                AppCompatTextView(context, attrs).decorate(attrs)

            "Button", "$APPCOMPAT_WIDGET.AppCompatButton" ->
                AppCompatButton(context, attrs).apply {
                    if (viewId == android.R.id.button1 ||
                        viewId == android.R.id.button2 ||
                        viewId == android.R.id.button3
                    ) {
                        decorateDialogButton()
                    } else if (isBorderlessButton(context, attrs)) {
                        decorateBorderlessButton()
                    } else {
                        decorateNormalButton(attrs)
                    }
                }

            "$APPCOMPAT_WIDGET.AppCompatCheckBox", "CheckBox" ->
                AppCompatCheckBox(context, attrs).decorate(attrs)
            "$APPCOMPAT_WIDGET.AppCompatRadioButton", "RadioButton" ->
                AppCompatRadioButton(context, attrs).decorate(attrs)
            "$APPCOMPAT_WIDGET.AppCompatEditText", "EditText" ->
                AppCompatEditText(context, attrs).decorate(attrs)
            "Switch" ->
                Switch(context, attrs).decorate(attrs)
            "$APPCOMPAT_WIDGET.SwitchCompat" ->
                SwitchCompat(context, attrs).decorate(attrs)
            "$APPCOMPAT_WIDGET.AppCompatSeekBar", "SeekBar" ->
                AppCompatSeekBar(context, attrs).decorate(attrs)
            "ProgressBar" ->
                ProgressBar(context, attrs).decorate()
            "$APPCOMPAT_VIEW.ActionMenuItemView" ->
                ThemeActionMenuItemView(context, attrs)

            "$APPCOMPAT_WIDGET.RecyclerView" ->
                RecyclerView(context, attrs).decorate()
            "$ANDROIDX_WIDGET.NestedScrollView" ->
                NestedScrollView(context, attrs).decorate()
            "ListView" ->
                ListView(context, attrs).decorate()
            "ScrollView" ->
                ScrollView(context, attrs).decorate()
            "androidx.viewpager.widget.ViewPager" ->
                ViewPager(context, attrs).decorate()

            "Spinner", "$APPCOMPAT_WIDGET.AppCompatSpinner" ->
                AppCompatSpinner(context, attrs).decorate(attrs)
            else -> null
        }
    }

    companion object {

        private const val ANDROIDX_WIDGET = "androidx.core.widget"
        private const val APPCOMPAT_WIDGET = "androidx.appcompat.widget"
        private const val APPCOMPAT_VIEW = "androidx.appcompat.view"

        private fun isBorderlessButton(
            context: Context?,
            attrs: AttributeSet?
        ): Boolean {
            if (context == null || attrs == null) {
                return false
            }
            val backgroundRes = context.resId(attrs, android.R.attr.background)
            if (backgroundRes == 0) {
                return false
            }
            val resName = context.resources.getResourceEntryName(backgroundRes)
            return resName.endsWith("btn_borderless_material")
        }
    }
}
