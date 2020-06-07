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
import com.google.android.material.navigation.NavigationView
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import xyz.aprildown.theme.tint.AppBarLayoutTint
import xyz.aprildown.theme.tint.BottomAppBarTint
import xyz.aprildown.theme.tint.BottomNavigationViewTint
import xyz.aprildown.theme.tint.ButtonTint
import xyz.aprildown.theme.tint.CardViewTint
import xyz.aprildown.theme.tint.CheckBoxTint
import xyz.aprildown.theme.tint.CheckedTextViewTint
import xyz.aprildown.theme.tint.ChipTint
import xyz.aprildown.theme.tint.CollapsingToolbarLayoutTint
import xyz.aprildown.theme.tint.EditTextTint
import xyz.aprildown.theme.tint.ExtendedFloatingActionButtonTint
import xyz.aprildown.theme.tint.FloatingActionButtonTint
import xyz.aprildown.theme.tint.HorizontalScrollViewTint
import xyz.aprildown.theme.tint.ImageButtonTint
import xyz.aprildown.theme.tint.ImageViewTint
import xyz.aprildown.theme.tint.ListViewTint
import xyz.aprildown.theme.tint.MaterialCardViewTint
import xyz.aprildown.theme.tint.NavigationViewTint
import xyz.aprildown.theme.tint.ProgressBarTint
import xyz.aprildown.theme.tint.RadioButtonTint
import xyz.aprildown.theme.tint.RecyclerViewTint
import xyz.aprildown.theme.tint.ScrollViewTint
import xyz.aprildown.theme.tint.SeekBarTint
import xyz.aprildown.theme.tint.SpinnerTint
import xyz.aprildown.theme.tint.SwitchMaterialTint
import xyz.aprildown.theme.tint.TabLayoutTint
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
        when (this) {

            is ProgressBar -> decorate(attrs, ProgressBarTint())

            is AppBarLayout -> decorate(attrs, AppBarLayoutTint())
            is CollapsingToolbarLayout -> decorate(attrs, CollapsingToolbarLayoutTint())
            is BottomAppBar -> decorate(attrs, BottomAppBarTint())
            is BottomNavigationView -> decorate(attrs, BottomNavigationViewTint())
            is Chip -> decorate(attrs, ChipTint())
            is ExtendedFloatingActionButton -> decorate(attrs, ExtendedFloatingActionButtonTint())
            is FloatingActionButton -> decorate(attrs, FloatingActionButtonTint())
            is MaterialCardView -> decorate(attrs, MaterialCardViewTint())
            is CardView -> decorate(attrs, CardViewTint())
            is NavigationView -> decorate(attrs, NavigationViewTint())
            is SwitchMaterial -> decorate(attrs, SwitchMaterialTint())
            is TabLayout -> decorate(attrs, TabLayoutTint())
            is TextInputLayout -> decorate(attrs, TextInputLayoutTint())

            is ScrollView -> decorate(attrs, ScrollViewTint())
            is HorizontalScrollView -> decorate(attrs, HorizontalScrollViewTint())
            is ListView -> decorate(attrs, ListViewTint())
            is RecyclerView -> decorate(attrs, RecyclerViewTint())

            is MaterialCheckBox -> decorate(attrs, CheckBoxTint())
            is MaterialRadioButton -> decorate(attrs, RadioButtonTint())

            is AppCompatSeekBar -> decorate(attrs, SeekBarTint())
            is AppCompatImageButton -> decorate(attrs, ImageButtonTint())
            is AppCompatImageView -> decorate(attrs, ImageViewTint())
            is AppCompatEditText -> decorate(attrs, EditTextTint())
            is AppCompatSpinner -> decorate(attrs, SpinnerTint())
            is AppCompatCheckedTextView -> decorate(attrs, CheckedTextViewTint())

            is AppCompatButton -> decorate(attrs, ButtonTint())
            is AppCompatTextView -> decorate(attrs, TextViewTint())
        }
    }
}
