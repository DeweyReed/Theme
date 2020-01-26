package xyz.aprildown.theme.tint

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.InsetDrawable
import android.view.View
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.MaterialShapeDrawable
import xyz.aprildown.theme.R
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.adjustAlpha
import xyz.aprildown.theme.utils.themeColor
import xyz.aprildown.theme.utils.toColorStateList

internal class CardViewTint : BaseTint<CardView>(
    attrs = R.styleable.Theme_MaterialCardView,
    defStyleAttr = R.attr.cardViewStyle,
    onTint = {
        val card = view
        matchThemeColor(R.styleable.Theme_MaterialCardView_cardBackgroundColor)?.let {
            card.setCardBackgroundColor(it)
        }
    }
)

/**
 * https://github.com/material-components/material-components-android/blob/master/docs/components/MaterialCardView.md
 */
internal class MaterialCardViewTint : BaseTint<MaterialCardView>(
    attrs = R.styleable.Theme_MaterialCardView,
    defStyleAttr = R.attr.materialCardViewStyle,
    onTint = {
        val card = view
        matchThemeColor(R.styleable.Theme_MaterialCardView_cardBackgroundColor)?.let {
            card.setCardBackgroundColor(it)
        }
        withColorOrResourceId(
            R.styleable.Theme_MaterialCardView_cardForegroundColor,
            applySolidColor = {
                (((card.foreground as? InsetDrawable)?.drawable) as? MaterialShapeDrawable)
                    ?.fillColor = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_card_view_foreground -> {
                        (((card.foreground as? InsetDrawable)?.drawable) as? MaterialShapeDrawable)
                            ?.fillColor = card.mtrl_card_view_foreground()
                    }
                }
            }
        )
        matchThemeColor(R.styleable.Theme_MaterialCardView_checkedIconTint)?.let {
            card.checkedIconTint = it.toColorStateList()
        }
        withColorOrResourceId(
            R.styleable.Theme_MaterialCardView_rippleColor,
            applySolidColor = {
                card.rippleColor = it.toColorStateList()
            },
            applyResource = {
                when (it) {
                    R.color.mtrl_card_view_ripple -> {
                        card.rippleColor = card.mtrl_card_view_ripple()
                    }
                }
            }
        )
        matchThemeColor(R.styleable.Theme_MaterialCardView_strokeColor)?.let {
            card.strokeColor = it
        }
    }
)

// R.color.mtrl_card_view_foreground
private fun View.mtrl_card_view_foreground(): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(R.attr.state_dragged),
            intArrayOf(-android.R.attr.state_checked, -R.attr.state_dragged)
        ),
        intArrayOf(
            Theme.get().colorPrimary.adjustAlpha(0.08f),
            context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.08f),
            Color.TRANSPARENT
        )
    )
}

// R.color.mtrl_card_view_ripple
private fun View.mtrl_card_view_ripple(): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(R.attr.state_dragged),
            intArrayOf(-android.R.attr.state_checked)
        ),
        intArrayOf(
            Theme.get().colorPrimary.adjustAlpha(0.2f),
            Color.TRANSPARENT,
            context.themeColor(R.attr.colorOnSurface).adjustAlpha(0.2f)
        )
    )
}
