package xyz.aprildown.theme

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Keep
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.theme.MaterialComponentsViewInflater
import xyz.aprildown.theme.utils.decorateWithTheme

@Keep // Make proguard keep this class as it's accessed reflectively by AppCompat
class ThemeViewInflater : MaterialComponentsViewInflater() {

    override fun createTextView(context: Context?, attrs: AttributeSet?): AppCompatTextView {
        return super.createTextView(context, attrs).decorateWithTheme(
            attrs,
            R.styleable.Theme_TextView,
            android.R.attr.textViewStyle
        ) { textView, helper ->
            helper.findThemeColor(R.styleable.Theme_TextView_android_textColor) {
                textView.setTextColor(it)
            }
        }
    }
}
