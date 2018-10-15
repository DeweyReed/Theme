package xyz.aprildown.theme.internal

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.*
import xyz.aprildown.theme.views.*

internal class InflationInterceptor(
    private val activity: AppCompatActivity,
    private val delegate: AppCompatDelegate?
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
        var view = name.viewForName(context, attrs, viewId)

        var viewBackgroundValue = ""
        var textColorValue = ""
        var hintTextColorValue = ""
        var tintValue = ""

        if (view.shouldIgnore()) {
            // Set view back to null so we can let AndroidX handle this view instead.
            view = null
        } else if (attrs != null) {
            val wizard = AttrWizard(context, attrs)
            viewBackgroundValue = wizard.getRawValue(android.R.attr.background)
            textColorValue = wizard.getRawValue(android.R.attr.textColor)
            hintTextColorValue = wizard.getRawValue(android.R.attr.textColorHint)
            tintValue = wizard.getRawValue(R.attr.tint)
        }

        // If view is null, let the activity try to create it
        if (view == null) {
            try {
                view = activity.onCreateView(parent, name, context, attrs)
                if (view == null) {
                    view = activity.onCreateView(name, context, attrs)
                }
            } catch (e: Throwable) {
                throw IllegalStateException(
                    "Unable to delegate inflation of $name to your Activity.",
                    e
                )
            }
        }
        // If it's still null, try the AppCompat delegate
        if (view == null && delegate != null && attrs != null) {
            try {
                view = delegate.createView(parent, name, context, attrs)
            } catch (e: Throwable) {
                throw IllegalStateException(
                    "Unable to delegate inflation of $name to AppCompat.",
                    e
                )
            }
        }
        // If it's still null, use the LayoutInflater directly
        if (view == null) {
            try {
                val layoutInflater = context.fixedLayoutInflater()
                view = layoutInflater.createView(name, getViewPrefix(name), attrs)
            } catch (e: Throwable) {
                throw IllegalStateException(
                    "Unable to delegate inflation of $name to normal LayoutInflater.", e
                )
            }
        }
        // If it's still null, explode
        if (view == null) {
            throw IllegalStateException("Unable to inflate $name! Please report as a GitHub issue.")
        }

        // If the view is blacklisted for apply, don't try to apply background theming, etc.
        if (name.isBlackListedForApply()) {
            return view
        }

        if (viewBackgroundValue.isNotEmpty()) {
            get().colorForAttrName(viewBackgroundValue)?.let {
                view.changeBackgroundColor(it)
            }
        }
        if (view is TextView) {
            get().colorForAttrName(textColorValue, context.textColorPrimary)?.let {
                view.changeTextColor(it)
            }
            get().colorForAttrName(hintTextColorValue)?.let {
                view.changeHintTextColor(it)
            }
        }
        if (tintValue.isNotEmpty()) {
            get().colorForAttrName(tintValue)?.let {
                (view as? ImageView)?.changeImageTint(it)
            }
        }

        return view
    }

    private fun String.viewForName(
        context: Context,
        attrs: AttributeSet?,
        @IdRes viewId: Int
    ): View? = when (this) {
        "androidx.drawerlayout.widget.DrawerLayout" ->
            ThemeDrawerLayout(context, attrs)
        "Toolbar", "$APPCOMPAT_WIDGET.Toolbar" ->
            ThemeToolbar(context, attrs)

//        "$APPCOMPAT_WIDGET.AppCompatTextView", "TextView" ->
//            if (viewId == id.snackbar_text) {
//                AestheticSnackBarTextView(context, attrs)
//            } else {
//                null
//            }

        "Button", "$APPCOMPAT_WIDGET.AppCompatButton" ->
            if (viewId == android.R.id.button1 ||
                viewId == android.R.id.button2 ||
                viewId == android.R.id.button3
            ) {
                ThemeDialogButton(context, attrs)
//            } else if (viewId == id.snackbar_action) {
//                AestheticSnackBarButton(context, attrs)
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
        "CheckedTextView", "$APPCOMPAT_WIDGET.AppCompatCheckedTextView" ->
            ThemeCheckedTextView(context, attrs)

//        "$APPCOMPAT_WIDGET.RecyclerView" ->
//            AestheticRecyclerView(context, attrs)
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

//        "$GOOGLE_MATERIAL.textfield.TextInputLayout" ->
//            AestheticTextInputLayout(context, attrs)
//        "$GOOGLE_MATERIAL.textfield.TextInputEditText" ->
//            AestheticTextInputEditText(context, attrs)

//        "$APPCOMPAT_WIDGET.CardView" ->
//            AestheticCardView(context, attrs)
//        "$GOOGLE_MATERIAL.tabs.TabLayout" ->
//            AestheticTabLayout(context, attrs)
//        "$GOOGLE_MATERIAL.navigation.NavigationView" ->
//            AestheticNavigationView(context, attrs)
//        "$GOOGLE_MATERIAL.bottomnavigation.BottomNavigationView" ->
//            AestheticBottomNavigationView(context, attrs)
//        "$GOOGLE_MATERIAL.floatingactionbutton.FloatingActionButton" ->
//            AestheticFab(context, attrs)

//        "androidx.coordinatorlayout.widget.CoordinatorLayout" ->
//            AestheticCoordinatorLayout(context, attrs)
//        "androidx.swiperefreshlayout.widget.SwipeRefreshLayout" ->
//            ThemeSwipeRefreshLayout(context, attrs)

        else -> {
            var result: View? = null
            for (delegate in get().delegates) {
                result = delegate.createView(context, attrs, this@viewForName, viewId)
                if (result != null) break
            }
            result
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

        private fun getViewPrefix(name: String): String {
            if (name.contains(".")) {
                // We have a full class, don't need a prefix
                return ""
            }
            // Else we have a framework class
            return when (name) {
                "View", "ViewStub", "SurfaceView", "TextureView" -> "android.view."
                else -> "android.widget."
            }
        }

        private fun View?.shouldIgnore() =
            this != null && (":theme_ignore" == tag || getTag(R.id.theme_ignore) != null)

        private fun String.isBlackListedForApply() =
            ("com.google.android.material.internal.NavigationMenuItemView" == this ||
                    "ViewStub" == this ||
                    "fragment" == this ||
                    "include" == this)
    }
}
