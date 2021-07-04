package xyz.aprildown.theme

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.annotation.StyleableRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
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
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import xyz.aprildown.theme.tint.AppBarLayoutTint
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
import xyz.aprildown.theme.tint.decorate

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

    fun View.applyDefaultTint(attrs: AttributeSet?) {
        // The code doesn't compile if we use when(this).
        when (val view = this) {

            is LinearProgressIndicator -> view.decorate(attrs, LinearProgressIndicatorTint())
            is CircularProgressIndicator -> view.decorate(attrs, CircularProgressIndicatorTint())
            is ProgressBar -> view.decorate(attrs, ProgressBarTint())
            is ContentLoadingProgressBar -> view.decorate(attrs, ProgressBarTint())

            is AppBarLayout -> view.decorate(attrs, AppBarLayoutTint())
            is CollapsingToolbarLayout -> view.decorate(attrs, CollapsingToolbarLayoutTint())
            is BottomAppBar -> view.decorate(attrs, BottomAppBarTint())
            is BottomNavigationView -> view.decorate(attrs, BottomNavigationViewTint())
            is NavigationRailView -> view.decorate(attrs, NavigationRailViewTint())
            is Chip -> view.decorate(attrs, ChipTint())
            is ExtendedFloatingActionButton -> view.decorate(
                attrs,
                ExtendedFloatingActionButtonTint()
            )
            is FloatingActionButton -> view.decorate(attrs, FloatingActionButtonTint())
            is MaterialCardView -> view.decorate(attrs, MaterialCardViewTint())
            is CardView -> view.decorate(attrs, CardViewTint())
            is NavigationView -> view.decorate(attrs, NavigationViewTint())
            is SwitchMaterial -> view.decorate(attrs, SwitchMaterialTint())
            is TabLayout -> view.decorate(attrs, TabLayoutTint())
            is TextInputLayout -> view.decorate(attrs, TextInputLayoutTint())
            is TextInputEditText -> view.decorate(attrs, TextInputEditTextTint())
            is Slider -> view.decorate(attrs, SliderTint())
            is RangeSlider -> view.decorate(attrs, RangeSliderTint())
            is ShapeableImageView -> view.decorate(attrs, ShapeableImageViewTint())

            is ScrollView -> view.decorate(attrs, ScrollViewTint())
            is HorizontalScrollView -> view.decorate(attrs, HorizontalScrollViewTint())
            is ListView -> view.decorate(attrs, ListViewTint())
            is RecyclerView -> view.decorate(attrs, RecyclerViewTint())

            is MaterialCheckBox -> view.decorate(attrs, CheckBoxTint())
            is MaterialRadioButton -> view.decorate(attrs, RadioButtonTint())

            is AppCompatSeekBar -> view.decorate(attrs, SeekBarTint())
            is AppCompatImageButton -> view.decorate(attrs, ImageButtonTint())
            is AppCompatImageView -> view.decorate(attrs, ImageViewTint())
            is AppCompatEditText -> view.decorate(attrs, EditTextTint())
            is AppCompatSpinner -> view.decorate(attrs, SpinnerTint())
            is AppCompatCheckedTextView -> view.decorate(attrs, CheckedTextViewTint())

            is AppCompatButton -> view.decorate(attrs, ButtonTint())
            is AppCompatTextView -> view.decorate(attrs, TextViewTint())
        }
    }
}
