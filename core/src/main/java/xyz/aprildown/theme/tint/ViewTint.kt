package xyz.aprildown.theme.tint

import android.util.AttributeSet
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Switch
import androidx.appcompat.widget.*
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.internal.AttrWizard
import xyz.aprildown.theme.utils.*

internal fun AppCompatTextView.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    Theme.get().colorForAttrName(wizard.getRawValue(android.R.attr.textColor))?.let {
        setTextColor(it)
    }
}

internal fun AppCompatCheckBox.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    val theme = Theme.get()
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTint(it, theme.isDark)
    }
}

internal fun AppCompatEditText.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    val theme = Theme.get()
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
    val wizard = AttrWizard(context, attrs)

    val theme = Theme.get()
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background))?.let {
        setBackgroundColor(it)
    }

    theme.colorForAttrName(wizard.getRawValue(R.attr.tint))?.let {
        setColorFilter(it)
    }
}

internal fun AppCompatImageButton.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    val theme = Theme.get()
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background))?.let {
        setBackgroundColor(it)
    }

    theme.colorForAttrName(wizard.getRawValue(R.attr.tint))?.let {
        setColorFilter(it)
    }
}

internal fun Switch.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    val theme = Theme.get()
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTint(it, theme.isDark)
    }
}

internal fun SwitchCompat.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    val theme = Theme.get()
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTint(it, theme.isDark)
    }
}

internal fun AppCompatRadioButton.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    val theme = Theme.get()
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTint(it, theme.isDark)
    }
}

internal fun AppCompatSeekBar.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    val theme = Theme.get()
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTint(it, theme.isDark)
    }
}

internal fun AppCompatSpinner.decorate(attrs: AttributeSet?) = apply {
    val wizard = AttrWizard(context, attrs)

    val theme = Theme.get()
    theme.colorForAttrName(wizard.getRawValue(android.R.attr.background), theme.colorAccent)?.let {
        setTintAuto(it, true, theme.isDark)
    }
}

internal fun ProgressBar.decorate() = apply {
    setTint(Theme.get().colorAccent)
}

internal fun ListView.decorate() = apply {
    EdgeEffectTint.setEdgeGlowColor(this, Theme.get().colorAccent)
}

internal fun ScrollView.decorate() = apply {
    EdgeEffectTint.setEdgeGlowColor(this, Theme.get().colorAccent)
}

internal fun NestedScrollView.decorate() = apply {
    EdgeEffectTint.setEdgeGlowColor(this, Theme.get().colorAccent)
}

internal fun RecyclerView.decorate() = apply {
    EdgeEffectTint.setEdgeGlowColor(this, Theme.get().colorAccent)
}

internal fun ViewPager.decorate() = apply {
    EdgeEffectTint.setEdgeGlowColor(this, Theme.get().colorAccent)
}

internal fun SwipeRefreshLayout.decorate() = apply {
    setColorSchemeColors(Theme.get().colorAccent)
}