package xyz.aprildown.theme

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.theme.MaterialComponentsViewInflater
import xyz.aprildown.theme.tint.AppBarLayoutTint
import xyz.aprildown.theme.tint.AutoCompleteTextViewTint
import xyz.aprildown.theme.tint.BottomAppBarTint
import xyz.aprildown.theme.tint.BottomNavigationViewTint
import xyz.aprildown.theme.tint.ButtonTint
import xyz.aprildown.theme.tint.CardViewTint
import xyz.aprildown.theme.tint.CheckBoxTint
import xyz.aprildown.theme.tint.CheckedTextViewTint
import xyz.aprildown.theme.tint.ChipTint
import xyz.aprildown.theme.tint.CircularProgressIndicatorTint
import xyz.aprildown.theme.tint.CollapsingToolbarLayoutTint
import xyz.aprildown.theme.tint.EditTextTint
import xyz.aprildown.theme.tint.ExtendedFloatingActionButtonTint
import xyz.aprildown.theme.tint.FloatingActionButtonTint
import xyz.aprildown.theme.tint.HorizontalScrollViewTint
import xyz.aprildown.theme.tint.ImageButtonTint
import xyz.aprildown.theme.tint.ImageViewTint
import xyz.aprildown.theme.tint.LinearProgressIndicatorTint
import xyz.aprildown.theme.tint.ListViewTint
import xyz.aprildown.theme.tint.MaterialCardViewTint
import xyz.aprildown.theme.tint.NavigationRailViewTint
import xyz.aprildown.theme.tint.NavigationViewTint
import xyz.aprildown.theme.tint.ProgressBarTint
import xyz.aprildown.theme.tint.RadioButtonTint
import xyz.aprildown.theme.tint.RangeSliderTint
import xyz.aprildown.theme.tint.RecyclerViewTint
import xyz.aprildown.theme.tint.ScrollViewTint
import xyz.aprildown.theme.tint.SeekBarTint
import xyz.aprildown.theme.tint.ShapeableImageViewTint
import xyz.aprildown.theme.tint.SliderTint
import xyz.aprildown.theme.tint.SpinnerTint
import xyz.aprildown.theme.tint.SwitchMaterialTint
import xyz.aprildown.theme.tint.TabLayoutTint
import xyz.aprildown.theme.tint.TextInputEditTextTint
import xyz.aprildown.theme.tint.TextInputLayoutTint
import xyz.aprildown.theme.tint.TextViewTint
import xyz.aprildown.theme.tint.ThemeToolbar
import xyz.aprildown.theme.tint.decorate

open class ThemeViewInflater : MaterialComponentsViewInflater() {

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

    override fun createAutoCompleteTextView(
        context: Context,
        attrs: AttributeSet?
    ): AppCompatAutoCompleteTextView {
        return super.createAutoCompleteTextView(context, attrs)
            .decorate(attrs, AutoCompleteTextViewTint())
    }

    // endregion MaterialComponentsViewInflater

    // region AppCompatViewInflater

    override fun createSeekBar(context: Context?, attrs: AttributeSet?): AppCompatSeekBar {
        return super.createSeekBar(context, attrs).decorate(attrs, SeekBarTint())
    }

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

    override fun createCheckedTextView(
        context: Context?,
        attrs: AttributeSet?
    ): AppCompatCheckedTextView {
        return super.createCheckedTextView(context, attrs).decorate(attrs, CheckedTextViewTint())
    }

    // endregion AppCompatViewInflater

    override fun createView(context: Context, name: String?, attrs: AttributeSet?): View? {
        if (name == null) return null
        // We may be in the edit mode where the instance isn't initialized.
        val theme = Theme.instance ?: return null
        if (!theme.enabled) return null

        for (delegate in theme.delegates) {
            val result = delegate.createView(context, name, attrs)
            if (result != null) {
                return result
            }
        }

        return when (name) {
            "androidx.appcompat.widget.AppCompatSeekBar" ->
                createSeekBar(context, attrs)
            "androidx.appcompat.widget.AppCompatImageView" ->
                createImageView(context, attrs)
            "androidx.appcompat.widget.AppCompatEditText" ->
                createEditText(context, attrs)
            "androidx.appcompat.widget.AppCompatSpinner" ->
                createSpinner(context, attrs)
            "androidx.appcompat.widget.AppCompatImageButton" ->
                createImageButton(context, attrs)

            "com.google.android.material.textview.MaterialTextView" ->
                createTextView(context, attrs)
            "com.google.android.material.button.MaterialButton" ->
                MaterialButton(context, attrs).decorate(attrs, ButtonTint())
            "com.google.android.material.checkbox.MaterialCheckBox" ->
                createCheckBox(context, attrs)
            "com.google.android.material.radiobutton.MaterialRadioButton" ->
                createRadioButton(context, attrs)

            "ProgressBar" ->
                ProgressBar(context, attrs).decorate(attrs, ProgressBarTint())
            "androidx.core.widget.ContentLoadingProgressBar" ->
                ContentLoadingProgressBar(context, attrs).decorate(attrs, ProgressBarTint())
            "com.google.android.material.progressindicator.LinearProgressIndicator" ->
                LinearProgressIndicator(context, attrs).decorate(
                    attrs,
                    LinearProgressIndicatorTint()
                )
            "com.google.android.material.progressindicator.CircularProgressIndicator" ->
                CircularProgressIndicator(context, attrs).decorate(
                    attrs,
                    CircularProgressIndicatorTint()
                )
            "com.google.android.material.appbar.AppBarLayout" ->
                AppBarLayout(context, attrs).decorate(attrs, AppBarLayoutTint())
            "com.google.android.material.appbar.CollapsingToolbarLayout" ->
                CollapsingToolbarLayout(context, attrs).decorate(
                    attrs,
                    CollapsingToolbarLayoutTint()
                )
            "androidx.appcompat.widget.Toolbar" ->
                ThemeToolbar(context, attrs)
            "com.google.android.material.appbar.MaterialToolbar" ->
                ThemeToolbar(context, attrs)
            "com.google.android.material.bottomappbar.BottomAppBar" ->
                BottomAppBar(context, attrs).decorate(attrs, BottomAppBarTint())
            "com.google.android.material.bottomnavigation.BottomNavigationView" ->
                BottomNavigationView(context, attrs).decorate(attrs, BottomNavigationViewTint())
            "com.google.android.material.navigationrail.NavigationRailView" ->
                NavigationRailView(context, attrs).decorate(attrs, NavigationRailViewTint())
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
            "com.google.android.material.switchmaterial.SwitchMaterial",
            "androidx.appcompat.widget.SwitchCompat" ->
                SwitchMaterial(context, attrs).decorate(attrs, SwitchMaterialTint())
            "com.google.android.material.tabs.TabLayout" ->
                TabLayout(context, attrs).decorate(attrs, TabLayoutTint())
            "com.google.android.material.textfield.TextInputLayout" ->
                TextInputLayout(context, attrs).decorate(attrs, TextInputLayoutTint())
            "com.google.android.material.textfield.TextInputEditText" ->
                TextInputEditText(context, attrs).decorate(attrs, TextInputEditTextTint())
            "com.google.android.material.slider.Slider" ->
                Slider(context, attrs).decorate(attrs, SliderTint())
            "com.google.android.material.slider.RangeSlider" ->
                RangeSlider(context, attrs).decorate(attrs, RangeSliderTint())
            "com.google.android.material.imageview.ShapeableImageView" ->
                ShapeableImageView(context, attrs).decorate(attrs, ShapeableImageViewTint())

            "ScrollView" ->
                ScrollView(context, attrs).decorate(attrs, ScrollViewTint())
            "HorizontalScrollView" ->
                HorizontalScrollView(context, attrs).decorate(attrs, HorizontalScrollViewTint())
            "ListView" ->
                ListView(context, attrs).decorate(attrs, ListViewTint())
            "androidx.recyclerview.widget.RecyclerView" ->
                RecyclerView(context, attrs).decorate(attrs, RecyclerViewTint())
            else -> null
        }
    }
}
