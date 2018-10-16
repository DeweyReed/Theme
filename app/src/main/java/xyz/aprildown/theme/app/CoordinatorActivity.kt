package xyz.aprildown.theme.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_coordinator.*
import kotlinx.android.synthetic.main.content_coordinator.*
import kotlinx.android.synthetic.main.list_item.view.*
import xyz.aprildown.theme.ThemeActivity

class CoordinatorActivity : ThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = NaiveAdapter()
    }

    private class NaiveAdapter : RecyclerView.Adapter<NaiveAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            )
        }

        override fun getItemCount(): Int = 20

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(position)
        }

        private class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            @SuppressLint("SetTextI18n")
            fun bind(pos: Int) {
                itemView.text1.text = "Title: $pos"
                itemView.text2.text = "Content: $pos"
            }
        }
    }

}
