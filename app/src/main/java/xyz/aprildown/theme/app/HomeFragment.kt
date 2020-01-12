package xyz.aprildown.theme.app

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_widget.view.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listHome.run {
            val itemAdapter =
                ItemAdapter<WidgetItem>()
            val fastAdapter =
                FastAdapter.with(itemAdapter)

            fastAdapter.onClickListener = { _, _, _, position ->
                findNavController().navigate(
                    when (position) {
                        0 -> R.id.textViewFragment
                        1 -> R.id.buttonFragment
                        2 -> R.id.compoundButtonFragment
                        else -> throw IllegalStateException()
                    }
                )
                true
            }

            adapter = fastAdapter

            itemAdapter.set(
                listOf(
                    "TextView",
                    "Button",
                    "CompoundButton"
                ).map { WidgetItem(it) }
            )
        }
    }

    private class WidgetItem(private val title: String) : AbstractItem<WidgetItem.ViewHolder>() {

        override val layoutRes: Int =
            R.layout.item_widget
        override val type: Int = R.layout.item_widget
        override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

        override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
            super.bindView(holder, payloads)
            holder.run {
                titleView.text = title
            }
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleView: TextView = view.textWidgetItem
        }
    }
}
