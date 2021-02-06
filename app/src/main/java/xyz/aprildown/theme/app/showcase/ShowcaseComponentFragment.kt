package xyz.aprildown.theme.app.showcase

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import xyz.aprildown.theme.app.R
import xyz.aprildown.theme.app.databinding.ComponentButtonTriggerBinding
import xyz.aprildown.theme.app.databinding.ComponentDrawerBinding
import xyz.aprildown.theme.app.databinding.ComponentSnackbarBinding
import xyz.aprildown.theme.app.databinding.FragmentShowcaseComponentBinding

class ShowcaseComponentFragment : Fragment(R.layout.fragment_showcase_component) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context = view.context
        val binding = FragmentShowcaseComponentBinding.bind(view)

        val itemAdapter = GenericItemAdapter()
        val fastAdapter = FastAdapter.with(itemAdapter)
        binding.listShowcaseComponent.adapter = fastAdapter
        binding.listShowcaseComponent.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        itemAdapter.add(
            SimpleComponent(R.layout.component_buttons),
            SimpleComponent(R.layout.component_fabs),
            SimpleComponent(R.layout.component_cards),
            SimpleComponent(R.layout.component_top_app_bar),
            SimpleComponent(R.layout.component_chips),
            DrawerComponent(),
            SimpleComponent(R.layout.component_text_field),
            SimpleComponent(R.layout.component_bottom_navigation),
            SimpleComponent(R.layout.component_switch),
            SimpleComponent(R.layout.component_radio_button),
            SimpleComponent(R.layout.component_checkbox),
            SimpleComponent(R.layout.component_bottom_app_bar),
            SimpleComponent(R.layout.component_tabs),
            SnackbarComponent(),
            ButtonTriggerComponent {
                MaterialAlertDialogBuilder(view.context)
                    .setTitle("Headline6")
                    .setMessage(R.string.lorem_ipsum)
                    .setPositiveButton("Button", null)
                    .setNegativeButton("Button", null)
                    .show()
            },
            ButtonTriggerComponent {
                BottomSheetFragment().show(childFragmentManager, null)
            }
        )
    }
}

private class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view)

private open class SimpleComponent(@LayoutRes componentLayoutRes: Int) :
    AbstractItem<SimpleViewHolder>() {
    override val layoutRes: Int = componentLayoutRes
    override val type: Int = componentLayoutRes
    override fun getViewHolder(v: View): SimpleViewHolder = SimpleViewHolder(v)
}

private class DrawerComponent : SimpleComponent(R.layout.component_drawer) {
    override fun bindView(holder: SimpleViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)
        ComponentDrawerBinding.bind(holder.itemView).run {
            drawerLayout.openDrawer(GravityCompat.START)
            navView.setNavigationItemSelectedListener { true }
            navView.setCheckedItem(R.id.nav_item_one)
        }
    }
}

private class SnackbarComponent : SimpleComponent(R.layout.component_snackbar) {

    override fun getViewHolder(v: View): SimpleViewHolder {
        return super.getViewHolder(v).apply {
            val binding = ComponentSnackbarBinding.bind(itemView)
            val container = binding.snackbarContainer
            val view = Snackbar.make(container, "Marked as favorite", Snackbar.LENGTH_INDEFINITE)
                .setAction("Action") {}
                .view
            view.updateLayoutParams<FrameLayout.LayoutParams> {
                gravity = Gravity.CENTER
            }
            container.addView(view)
        }
    }
}

private class ButtonTriggerComponent(
    private val onClick: () -> Unit
) : SimpleComponent(R.layout.component_button_trigger) {
    override fun bindView(holder: SimpleViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)
        ComponentButtonTriggerBinding.bind(holder.itemView).button.setOnClickListener {
            onClick.invoke()
        }
    }
}

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }
}
