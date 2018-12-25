@file:Suppress("SpellCheckingInspection")

package xyz.aprildown.theme.material

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import xyz.aprildown.theme.InflationDelegate
import xyz.aprildown.theme.material.tint.decorate
import xyz.aprildown.theme.material.views.*

object MaterialInflationDelegate : InflationDelegate {
    override fun createView(
        context: Context,
        attrs: AttributeSet?,
        name: String,
        viewId: Int
    ): View? = when (name) {
        "$GOOGLE_MATERIAL.snackbar.SnackbarContentLayout" ->
            ThemeSnackBarContentLayout(context, attrs)
        "Button", "$GOOGLE_MATERIAL.button.MaterialButton" ->
            MaterialButton(context, attrs).decorate(attrs)
        "$GOOGLE_MATERIAL.textfield.TextInputLayout" ->
            TextInputLayout(context, attrs).decorate()
        "$GOOGLE_MATERIAL.textfield.TextInputEditText" ->
            TextInputEditText(context, attrs).decorate()
        "$GOOGLE_MATERIAL.tabs.TabLayout" ->
            ThemeTabLayout(context, attrs)
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

    private const val GOOGLE_MATERIAL = "com.google.android.material"
}