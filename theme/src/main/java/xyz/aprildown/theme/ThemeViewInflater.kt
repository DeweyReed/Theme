package xyz.aprildown.theme

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Keep
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.theme.MaterialComponentsViewInflater
import xyz.aprildown.theme.tint.ButtonTint
import xyz.aprildown.theme.tint.CheckBoxTint
import xyz.aprildown.theme.tint.ImageViewTint
import xyz.aprildown.theme.tint.RadioButtonTint
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

    // endregion AppCompatViewInflater
}
