package xyz.aprildown.theme.tint

import android.widget.TextView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textview.MaterialTextView
import xyz.aprildown.theme.R

/**
 * [MaterialTextView]
 * https://github.com/material-components/material-components-android/blob/master/docs/components/MaterialTextView.md
 * [R.style.Widget_MaterialComponents_TextView]
 */
internal class TextViewTint : BaseTint<AppCompatTextView>(
    attrs = R.styleable.Theme_TextView,
    defStyleAttr = android.R.attr.textViewStyle,
    onTint = {
        decorateTextView()
    }
)

internal fun ThemeHelper<*>.decorateTextView() {
    val textView = view as TextView
    matchThemeColor(R.styleable.Theme_TextView_android_textColor)?.let {
        textView.setTextColor(it)
    }
    matchThemeColor(R.styleable.Theme_TextView_android_textColorHint)?.let {
        textView.setHintTextColor(it)
    }
    matchThemeColor(R.styleable.Theme_TextView_android_textColorLink)?.let {
        textView.setLinkTextColor(it)
    }
    matchThemeColor(R.styleable.Theme_TextView_android_textColorHighlight)?.let {
        textView.highlightColor = it
    }
}

/**
 * [R.style.Widget_AppCompat_AutoCompleteTextView]
 */
internal class AutoCompleteTextViewTint : BaseTint<AppCompatAutoCompleteTextView>(
    attrs = R.styleable.Theme_TextView,
    defStyleAttr = R.attr.autoCompleteTextViewStyle,
    onTint = {
        decorateTextView()
    }
)
