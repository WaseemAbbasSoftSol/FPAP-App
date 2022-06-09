package com.softsolutions.fpap.ui.main.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.softsolutions.fpap.model.Dashboard
import com.softsolutions.fpap.R
import com.softsolutions.fpap.ui.common.OnListItemClickListener

class DashboardAdapter(
    val context: Context,
    private val list: List<Dashboard>,
    private val listener:OnListItemClickListener<Dashboard>

) :
    RecyclerView.Adapter<DashboardAdapter.ItemRecyclerViewHolder>() {

    class ItemRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_title)
        var cardView:CardView= itemView.findViewById(R.id.cardview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecyclerViewHolder {
        return ItemRecyclerViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_dashboard,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ItemRecyclerViewHolder, position: Int) {
        val dashboardObject = list[position]
          holder.title.text = dashboardObject.title
        holder.cardView.setOnClickListener {
            listener.onItemClick(dashboardObject,position)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

}