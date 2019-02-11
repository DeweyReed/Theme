@file:Suppress("SpellCheckingInspection")

package xyz.aprildown.theme.internal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.Switch
import androidx.appcompat.widget.*
import androidx.cardview.widget.CardView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import xyz.aprildown.theme.InflationDelegate
import xyz.aprildown.theme.tint.decorate
import xyz.aprildown.theme.utils.resId
import xyz.aprildown.theme.views.ThemeActionMenuItemView
import xyz.aprildown.theme.views.ThemeDrawerLayout
import xyz.aprildown.theme.views.ThemeToolbar
import xyz.aprildown.theme.views.material.*

internal class InflationInterceptor(
    private val delegates: Array<out InflationDelegate>
) : LayoutInflater.Factory2 {

    override fun onCreateView(
        name: String,
        context: Context,
        attrs: AttributeSet?
    ): View? {
        return onCreateView(null, name, context, attrs)
    }

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
        view = name.viewForName(context, attrs)
        return view
    }

    private fun String.viewForName(
        context: Context,
        attrs: AttributeSet?
    ): View? {
        return when (this) {
            "TextView", "$APPCOMPAT_WIDGET.AppCompatTextView" ->
                AppCompatTextView(context, attrs).decorate(attrs)
            "ImageView", "$APPCOMPAT_WIDGET.AppCompatImageView" ->
                AppCompatImageView(context, attrs).decorate(attrs)
            "Button", "$GOOGLE_MATERIAL.button.MaterialButton", "$APPCOMPAT_WIDGET.AppCompatButton" ->
                MaterialButton(context, attrs).decorate(attrs)
            "EditText", "$APPCOMPAT_WIDGET.AppCompatEditText" ->
                AppCompatEditText(context, attrs).decorate(attrs)
            "Spinner", "$APPCOMPAT_WIDGET.AppCompatSpinner" ->
                AppCompatSpinner(context, attrs).decorate(attrs)
            "ImageButton", "$APPCOMPAT_WIDGET.AppCompatImageButton" ->
                AppCompatImageButton(context, attrs).decorate(attrs)
            "CheckBox", "$APPCOMPAT_WIDGET.AppCompatCheckBox" ->
                AppCompatCheckBox(context, attrs).decorate(attrs)
            "RadioButton", "$APPCOMPAT_WIDGET.AppCompatRadioButton" ->
                AppCompatRadioButton(context, attrs).decorate(attrs)
            "SeekBar", "$APPCOMPAT_WIDGET.AppCompatSeekBar" ->
                AppCompatSeekBar(context, attrs).decorate(attrs)

            "Switch" ->
                Switch(context, attrs).decorate(attrs)
            "ProgressBar" ->
                ProgressBar(context, attrs).decorate()
            "Toolbar", "$APPCOMPAT_WIDGET.Toolbar" ->
                ThemeToolbar(context, attrs)
            "$APPCOMPAT_VIEW.ActionMenuItemView" ->
                ThemeActionMenuItemView(context, attrs)
            "$APPCOMPAT_WIDGET.SwitchCompat" ->
                SwitchCompat(context, attrs).decorate(attrs)

//            "ListView" ->
//                ListView(context, attrs).decorate()
//            "ScrollView" ->
//                ScrollView(context, attrs).decorate()
//            "$APPCOMPAT_WIDGET.RecyclerView" ->
//                RecyclerView(context, attrs).decorate()
//            "$ANDROIDX_WIDGET.NestedScrollView" ->
//                NestedScrollView(context, attrs).decorate()
//            "androidx.viewpager.widget.ViewPager" ->
//                ViewPager(context, attrs).decorate()
            "androidx.drawerlayout.widget.DrawerLayout" ->
                ThemeDrawerLayout(context, attrs)
            "androidx.swiperefreshlayout.widget.SwipeRefreshLayout" ->
                SwipeRefreshLayout(context, attrs).decorate()

            "androidx.cardview.widget.CardView" ->
                CardView(context, attrs).decorate(attrs)

            "$GOOGLE_MATERIAL.card.MaterialCardView" ->
                MaterialCardView(context, attrs).decorate(attrs)
            "$GOOGLE_MATERIAL.snackbar.SnackbarContentLayout" ->
                ThemeSnackBarContentLayout(context, attrs)
            "$GOOGLE_MATERIAL.textfield.TextInputLayout" ->
                TextInputLayout(context, attrs).decorate()
            "$GOOGLE_MATERIAL.textfield.TextInputEditText" ->
                TextInputEditText(context, attrs).decorate(attrs)
            "$GOOGLE_MATERIAL.tabs.TabLayout" ->
                TabLayout(context, attrs).decorate(attrs)
            "$GOOGLE_MATERIAL.navigation.NavigationView" ->
                NavigationView(context, attrs).decorate()
            "$GOOGLE_MATERIAL.bottomnavigation.BottomNavigationView" ->
                ThemeBottomNavigationView(context, attrs)
            "$GOOGLE_MATERIAL.floatingactionbutton.FloatingActionButton" ->
                ThemeFloatingActionButton(context, attrs)
            "$GOOGLE_MATERIAL.bottomappbar.BottomAppBar" ->
                ThemeBottomAppBar(context, attrs)
            "androidx.coordinatorlayout.widget.CoordinatorLayout" ->
                ThemeCoordinatorLayout(context, attrs)

            else -> null
        }
    }

    companion object {

        //        private const val ANDROIDX_WIDGET = "androidx.core.widget"
        private const val APPCOMPAT_WIDGET = "androidx.appcompat.widget"
        private const val APPCOMPAT_VIEW = "androidx.appcompat.view"

        private const val GOOGLE_MATERIAL = "com.google.android.material"

    }
}
