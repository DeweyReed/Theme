package xyz.aprildown.theme.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main2.view.*
import org.jetbrains.anko.startActivity

class MainFragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val c = view.context
        view.btnToDrawer.setOnClickListener {
            c.startActivity<DrawerActivity>()
        }
        view.btnToCoordinator.setOnClickListener {
            c.startActivity<CoordinatorActivity>()
        }
    }
}