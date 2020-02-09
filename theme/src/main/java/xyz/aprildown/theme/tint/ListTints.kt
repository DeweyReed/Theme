package xyz.aprildown.theme.tint

import android.widget.EdgeEffect
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import xyz.aprildown.theme.Theme
import xyz.aprildown.theme.utils.isQOrLater

internal class ScrollViewTint : BaseTint<ScrollView>(
    attrs = intArrayOf(),
    onTint = {
        val scrollView = view
        if (isQOrLater()) {
            scrollView.setEdgeEffectColor(Theme.get().colorPrimary)
        }
    }
)

internal class HorizontalScrollViewTint : BaseTint<HorizontalScrollView>(
    attrs = intArrayOf(),
    onTint = {
        val scrollView = view
        if (isQOrLater()) {
            scrollView.setEdgeEffectColor(Theme.get().colorPrimary)
        }
    }
)

internal class ListViewTint : BaseTint<ListView>(
    attrs = intArrayOf(),
    onTint = {
        val listView = view
        if (isQOrLater()) {
            listView.setEdgeEffectColor(Theme.get().colorPrimary)
        }
    }
)

internal class RecyclerViewTint : BaseTint<RecyclerView>(
    attrs = intArrayOf(),
    onTint = {
        val recyclerView = view
        val edgeColor = Theme.get().colorPrimary
        recyclerView.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
                return super.createEdgeEffect(view, direction).apply {
                    color = edgeColor
                }
            }
        }
    }
)
