package xyz.aprildown.theme.app.widgets

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_widget.view.*
import xyz.aprildown.theme.app.R

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listHome.run {
            val itemAdapter =
                ItemAdapter<WidgetItem>()
            val fastAdapter =
                FastAdapter.with(itemAdapter)

            fastAdapter.onClickListener = { _, _, item, _ ->
                findNavController().navigate(item.pair.second)
                true
            }

            fastAdapter.onLongClickListener = { _, _, item, _ ->
                Snackbar.make(view, item.pair.first, Snackbar.LENGTH_SHORT)
                    .setAction("Action") {}
                    .show()
                true
            }

            adapter = fastAdapter

            itemAdapter.set(
                listOf(
                    "Text View" to R.id.textViewFragment,
                    "Text Input" to R.id.textInputFragment,
                    "Button" to R.id.buttonFragment,
                    "Compound Button" to R.id.compoundButtonFragment,
                    "Image" to R.id.imageFragment,
                    "App Bar" to R.id.appBarFragment,
                    "Chip" to R.id.chipFragment,
                    "Fab" to R.id.fabFragment,
                    "Card" to R.id.cardFragment,
                    "Bar" to R.id.barFragment,
                    "Collapsing Toolbar Activity" to R.id.collapsingToolbarActivity
                ).map { WidgetItem(it) }
            )
        }
    }

    private class WidgetItem(val pair: Pair<String, Int>) : AbstractItem<WidgetItem.ViewHolder>() {

        override val layoutRes: Int =
            R.layout.item_widget
        override val type: Int = R.layout.item_widget
        override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

        override fun bindView(holder: ViewHolder, payloads: List<Any>) {
            super.bindView(holder, payloads)
            holder.run {
                titleView.text = pair.first
            }
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleView: TextView = view.textWidgetItem
        }
    }
}
