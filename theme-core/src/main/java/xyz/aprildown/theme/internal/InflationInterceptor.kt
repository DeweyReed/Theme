package xyz.aprildown.theme.internal

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.resId
import xyz.aprildown.theme.views.*

internal class InflationInterceptor : LayoutInflater.Factory2 {

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
        for (delegate in get().delegates) {
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
    ): View? = when (this) {
        "ImageView", "$APPCOMPAT_WIDGET.AppCompatImageView" ->
            ThemeImageView(context, attrs)
        "ImageButton", "$APPCOMPAT_WIDGET.AppCompatImageButton" ->
            ThemeImageButton(context, attrs)

        "androidx.drawerlayout.widget.DrawerLayout" ->
            ThemeDrawerLayout(context, attrs)
        "Toolbar", "$APPCOMPAT_WIDGET.Toolbar" ->
            ThemeToolbar(context, attrs)

        "$APPCOMPAT_WIDGET.AppCompatTextView", "TextView" ->
            ThemeTextView(context, attrs)

        "Button", "$APPCOMPAT_WIDGET.AppCompatButton" ->
            if (viewId == android.R.id.button1 ||
                viewId == android.R.id.button2 ||
                viewId == android.R.id.button3
            ) {
                ThemeDialogButton(context, attrs)
            } else if (isBorderlessButton(context, attrs)) {
                ThemeBorderlessButton(context, attrs)
            } else {
                ThemeButton(context, attrs)
            }

        "$APPCOMPAT_WIDGET.AppCompatCheckBox", "CheckBox" ->
            ThemeCheckBox(context, attrs)
        "$APPCOMPAT_WIDGET.AppCompatRadioButton", "RadioButton" ->
            ThemeRadioButton(context, attrs)
        "$APPCOMPAT_WIDGET.AppCompatEditText", "EditText" ->
            ThemeEditText(context, attrs)
        "Switch" ->
            ThemeSwitch(context, attrs)
        "$APPCOMPAT_WIDGET.SwitchCompat" ->
            ThemeSwitchCompat(context, attrs)
        "$APPCOMPAT_WIDGET.AppCompatSeekBar", "SeekBar" ->
            ThemeSeekBar(context, attrs)
        "ProgressBar" ->
            ThemeProgressBar(context, attrs)
        "$APPCOMPAT_VIEW.ActionMenuItemView" ->
            ThemeActionMenuItemView(context, attrs)

        "$ANDROIDX_WIDGET.NestedScrollView" ->
            ThemeNestedScrollView(context, attrs)
        "ListView" ->
            ThemeListView(context, attrs)
        "ScrollView" ->
            ThemeScrollView(context, attrs)
        "androidx.viewpager.widget.ViewPager" ->
            ThemeViewPager(context, attrs)

        "Spinner", "$APPCOMPAT_WIDGET.AppCompatSpinner" ->
            ThemeSpinner(context, attrs)
        else -> null
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
