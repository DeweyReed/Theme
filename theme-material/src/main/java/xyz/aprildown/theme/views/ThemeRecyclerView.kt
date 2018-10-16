package xyz.aprildown.theme.views

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import xyz.aprildown.theme.Theme.Companion.get
import xyz.aprildown.theme.utils.EdgeGlowUtil
import java.lang.reflect.Field

class ThemeRecyclerView(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    init {
        invalidateColors(get().colorAccent)
    }

    private fun invalidateColors(color: Int) =
        setEdgeGlowColor(this, color, null)

    companion object {
        private var RECYCLER_VIEW_FIELD_EDGE_GLOW_TOP: Field? = null
        private var RECYCLER_VIEW_FIELD_EDGE_GLOW_LEFT: Field? = null
        private var RECYCLER_VIEW_FIELD_EDGE_GLOW_RIGHT: Field? = null
        private var RECYCLER_VIEW_FIELD_EDGE_GLOW_BOTTOM: Field? = null

        fun setEdgeGlowColor(
            scrollView: RecyclerView,
            @ColorInt color: Int,
            requestedScrollListener: RecyclerView.OnScrollListener?
        ) {
            var scrollListener = requestedScrollListener
            invalidateRecyclerViewFields()
            invalidateRecyclerViewFields()
            if (scrollListener == null) {
                scrollListener = object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(
                        recyclerView: RecyclerView,
                        newState: Int
                    ) {
                        super.onScrollStateChanged(recyclerView, newState)
                        setEdgeGlowColor(recyclerView, color, this)
                    }
                }
                scrollView.addOnScrollListener(scrollListener)
            }
            try {
                var ee = RECYCLER_VIEW_FIELD_EDGE_GLOW_TOP!!.get(scrollView)
                EdgeGlowUtil.setEffectColor(ee, color)
                ee = RECYCLER_VIEW_FIELD_EDGE_GLOW_BOTTOM!!.get(scrollView)
                EdgeGlowUtil.setEffectColor(ee, color)
                ee = RECYCLER_VIEW_FIELD_EDGE_GLOW_LEFT!!.get(scrollView)
                EdgeGlowUtil.setEffectColor(ee, color)
                ee = RECYCLER_VIEW_FIELD_EDGE_GLOW_RIGHT!!.get(scrollView)
                EdgeGlowUtil.setEffectColor(ee, color)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        private fun invalidateRecyclerViewFields() {
            if (RECYCLER_VIEW_FIELD_EDGE_GLOW_TOP != null &&
                RECYCLER_VIEW_FIELD_EDGE_GLOW_LEFT != null &&
                RECYCLER_VIEW_FIELD_EDGE_GLOW_RIGHT != null &&
                RECYCLER_VIEW_FIELD_EDGE_GLOW_BOTTOM != null
            ) {
                RECYCLER_VIEW_FIELD_EDGE_GLOW_TOP!!.isAccessible = true
                RECYCLER_VIEW_FIELD_EDGE_GLOW_LEFT!!.isAccessible = true
                RECYCLER_VIEW_FIELD_EDGE_GLOW_RIGHT!!.isAccessible = true
                RECYCLER_VIEW_FIELD_EDGE_GLOW_BOTTOM!!.isAccessible = true
                return
            }
            val cls = RecyclerView::class.java
            for (f in cls.declaredFields) {
                when (f.name) {
                    "mTopGlow" -> {
                        f.isAccessible = true
                        RECYCLER_VIEW_FIELD_EDGE_GLOW_TOP = f
                    }
                    "mBottomGlow" -> {
                        f.isAccessible = true
                        RECYCLER_VIEW_FIELD_EDGE_GLOW_BOTTOM = f
                    }
                    "mLeftGlow" -> {
                        f.isAccessible = true
                        RECYCLER_VIEW_FIELD_EDGE_GLOW_LEFT = f
                    }
                    "mRightGlow" -> {
                        f.isAccessible = true
                        RECYCLER_VIEW_FIELD_EDGE_GLOW_RIGHT = f
                    }
                }
            }
        }
    }
}
