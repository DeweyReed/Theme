package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Switch
import androidx.appcompat.widget.*
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.*

internal fun AppCompatTextView.decorate(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    Theme.get(c).colorForAttrName(wizard.getRawValue(android.R.attr.textColor))?.let {
        setTextColor(it)
    }
}

internal fun AppCompatButton.decorateNormalButton(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    val theme = Theme.get(c)
    theme.colorForAttrName(
        wizard.getRawValue(android.R.attr.background),
        theme.colorAccent
    )?.let { color ->
        val isDark = theme.isDark
        setTintAuto(color, true, isDark)
        val textColorSl = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(-android.R.attr.state_enabled)
            ),
            intArrayOf(
                if (ColorUtils.isLightColor(color)) Color.BLACK else Color.WHITE,
                if (isDark) Color.WHITE else Color.BLACK
            )
        )
        setTextColor(textColorSl)

        // Hack around button color not updating
        isEnabled = !isEnabled
        isEnabled = !isEnabled
    }
}

internal fun AppCompatButton.decorateBorderlessButton() = apply {
    val color = Theme.get(context).colorAccent
    val textColorSl = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled)
        ),
        intArrayOf(
            color,
            ColorUtils.adjustAlpha(color, 0.56f)
        )
    )
    setTextColor(textColorSl)

    // Hack around button color not updating
    isEnabled = !isEnabled
    isEnabled = !isEnabled
}

internal fun AppCompatButton.decorateDialogButton() = apply {
    setTextColor(Theme.get(context).colorAccent)
}

internal fun AppCompatCheckBox.decorate(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    val theme = Theme.get(c)
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTint(it, theme.isDark)
    }
}

internal fun AppCompatEditText.decorate(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    val theme = Theme.get(c)
    theme.colorForAttrName(wizard.getRawValue(R.attr.tint), theme.colorAccent)?.let {
        setTintAuto(it, true, theme.isDark)
    }

    theme.colorForAttrName(
        wizard.getRawValue(android.R.attr.textColor),
        context.textColorPrimary
    )?.let {
        setTextColor(it)
    }

    theme.colorForAttrName(
        wizard.getRawValue(android.R.attr.textColorHint),
        context.textColorSecondary
    )?.let {
        setHintTextColor(it)
    }
}

internal fun AppCompatImageView.decorate(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    val theme = Theme.get(c)
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background))?.let {
        setBackgroundColor(it)
    }

    theme.colorForAttrName(wizard.getRawValue(R.attr.tint))?.let {
        setColorFilter(it)
    }
}

internal fun AppCompatImageButton.decorate(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    val theme = Theme.get(c)
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background))?.let {
        setBackgroundColor(it)
    }

    theme.colorForAttrName(wizard.getRawValue(R.attr.tint))?.let {
        setColorFilter(it)
    }
}

internal fun Switch.decorate(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    val theme = Theme.get(c)
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTint(it, theme.isDark)
    }
}

internal fun SwitchCompat.decorate(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    val theme = Theme.get(c)
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTint(it, theme.isDark)
    }
}

internal fun AppCompatRadioButton.decorate(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    val theme = Theme.get(c)
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTint(it, theme.isDark)
    }
}

internal fun AppCompatSeekBar.decorate(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    val theme = Theme.get(c)
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTint(it, theme.isDark)
    }
}

internal fun AppCompatSpinner.decorate(attrs: AttributeSet?) = apply {
    val c = context
    val wizard = AttrWizard(c, attrs)

    val theme = Theme.get(c)
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTintAuto(it, true, theme.isDark)
    }
}

internal fun ProgressBar.decorate() = apply {
    setTint(Theme.get(context).colorAccent)
}

internal fun ListView.decorate() = apply {
    EdgeEffectTint.setEdgeGlowColor(this, Theme.get(context).colorAccent)
}

internal fun ScrollView.decorate() = apply {
    EdgeEffectTint.setEdgeGlowColor(this, Theme.get(context).colorAccent)
}

internal fun NestedScrollView.decorate() = apply {
    EdgeEffectTint.setEdgeGlowColor(this, Theme.get(context).colorAccent)
}

internal fun RecyclerView.decorate() = apply {
    EdgeEffectTint.setEdgeGlowColor(this, Theme.get(context).colorAccent)
}

internal fun ViewPager.decorate() = apply {
    EdgeEffectTint.setEdgeGlowColor(this, Theme.get(context).colorAccent)
}