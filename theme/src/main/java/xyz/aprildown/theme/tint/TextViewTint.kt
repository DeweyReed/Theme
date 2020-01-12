package xyz.aprildown.theme.tint

import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.textview.MaterialTextView
import xyz.aprildown.theme.R

/**
 * [MaterialTextView]
 * https://github.com/material-components/material-components-android/blob/master/docs/components/MaterialTextView.md
 */
internal class TextViewTint : BaseTint<AppCompatTextView>(
    attrs = R.styleable.Theme_TextView,
    defStyleAttr = android.R.attr.textViewStyle,
    onTint = { helper ->
        val textView = helper.view
        helper.findThemeColor(R.styleable.Theme_TextView_android_textColor)?.let {
            textView.setTextColor(it)
        }
    }
)
