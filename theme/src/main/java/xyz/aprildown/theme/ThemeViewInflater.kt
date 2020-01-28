package xyz.aprildown.theme

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout
import com.google.android.material.theme.MaterialComponentsViewInflater
import xyz.aprildown.theme.tint.BottomAppBarTint
import xyz.aprildown.theme.tint.BottomNavigationViewTint
import xyz.aprildown.theme.tint.ButtonTint
import xyz.aprildown.theme.tint.CardViewTint
import xyz.aprildown.theme.tint.CheckBoxTint
import xyz.aprildown.theme.tint.ChipTint
import xyz.aprildown.theme.tint.EditTextTint
import xyz.aprildown.theme.tint.ExtendedFloatingActionButtonTint
import xyz.aprildown.theme.tint.FloatingActionButtonTint
import xyz.aprildown.theme.tint.ImageButtonTint
import xyz.aprildown.theme.tint.ImageViewTint
import xyz.aprildown.theme.tint.MaterialCardViewTint
import xyz.aprildown.theme.tint.NavigationViewTint
import xyz.aprildown.theme.tint.RadioButtonTint
import xyz.aprildown.theme.tint.SpinnerTint
import xyz.aprildown.theme.tint.SwitchMaterialTint
import xyz.aprildown.theme.tint.TabLayoutTint
import xyz.aprildown.theme.tint.TextViewTint
import xyz.aprildown.theme.tint.decorate

@Keep // Make proguard keep this class as it's accessed reflectively by AppCompat
class ThemeViewInflater : MaterialComponentsViewInflater() {

    // region MaterialComponentsViewInflater

    override fun createTextView(context: Context?, attrs: AttributeSet?): AppCompatTextView {
        return super.createTextView(context, attrs).decorate(attrs, TextViewTint())
    }

    override fun createButton(context: Context, attrs: AttributeSet): AppCompatButton {
        return super.createButton(context, attrs).decorate(attrs, ButtonTint())
    }

    override fun createCheckBox(context: Context?, attrs: AttributeSet?): AppCompatCheckBox {
        return super.createCheckBox(context, attrs).decorate(attrs, CheckBoxTint())
    }

    override fun createRadioButton(context: Context?, attrs: AttributeSet?): AppCompatRadioButton {
        return super.createRadioButton(context, attrs).decorate(attrs, RadioButtonTint())
    }

    // endregion MaterialComponentsViewInflater

    // region AppCompatViewInflater

    override fun createImageView(context: Context?, attrs: AttributeSet?): AppCompatImageView {
        return super.createImageView(context, attrs).decorate(attrs, ImageViewTint())
    }

    override fun createEditText(context: Context?, attrs: AttributeSet?): AppCompatEditText {
        return super.createEditText(context, attrs).decorate(attrs, EditTextTint())
    }

    override fun createSpinner(context: Context?, attrs: AttributeSet?): AppCompatSpinner {
        return super.createSpinner(context, attrs).decorate(attrs, SpinnerTint())
    }

    override fun createImageButton(context: Context?, attrs: AttributeSet?): AppCompatImageButton {
        return super.createImageButton(context, attrs).decorate(attrs, ImageButtonTint())
    }

    // endregion AppCompatViewInflater

    override fun createView(context: Context, name: String?, attrs: AttributeSet?): View? {
        return when (name) {
            "com.google.android.material.bottomappbar.BottomAppBar" ->
                BottomAppBar(context, attrs).decorate(attrs, BottomAppBarTint())
            "com.google.android.material.bottomnavigation.BottomNavigationView" ->
                BottomNavigationView(context, attrs).decorate(attrs, BottomNavigationViewTint())
            "com.google.android.material.chip.Chip" ->
                Chip(context, attrs).decorate(attrs, ChipTint())
            "com.google.android.material.floatingactionbutton.FloatingActionButton" ->
                FloatingActionButton(context, attrs).decorate(attrs, FloatingActionButtonTint())
            "com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton" ->
                ExtendedFloatingActionButton(context, attrs)
                    .decorate(attrs, ExtendedFloatingActionButtonTint())
            "androidx.cardview.widget.CardView" ->
                CardView(context, attrs).decorate(attrs, CardViewTint())
            "com.google.android.material.card.MaterialCardView" ->
                MaterialCardView(context, attrs).decorate(attrs, MaterialCardViewTint())
            "com.google.android.material.navigation.NavigationView" ->
                NavigationView(context, attrs).decorate(attrs, NavigationViewTint())
            "com.google.android.material.switchmaterial.SwitchMaterial" ->
                SwitchMaterial(context, attrs).decorate(attrs, SwitchMaterialTint())
            "com.google.android.material.tabs.TabLayout" ->
                TabLayout(context, attrs).decorate(attrs, TabLayoutTint())
            else -> super.createView(context, name, attrs)
        }
    }
}
