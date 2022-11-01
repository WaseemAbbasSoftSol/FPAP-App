package com.lmslsbe.ui.main.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lmslsbe.R
import com.lmslsbe.model.Dashboard
import com.lmslsbe.ui.common.OnListItemClickListener
import de.hdodenhof.circleimageview.CircleImageView

class DashboardAdapter(
    val context: Context,
    private val list: List<Dashboard>,
    private val listener: OnListItemClickListener<Dashboard>,
    private val isDashboard:Boolean,
    private val isUrduMedium:Boolean=false

) :
    RecyclerView.Adapter<DashboardAdapter.ItemRecyclerViewHolder>() {

    class ItemRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_title)
        var cardView:CardView= itemView.findViewById(R.id.cardview)
        lateinit var support:CircleImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecyclerViewHolder {
        if (isDashboard){

                return ItemRecyclerViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_dashboard,
                        parent,
                        false
                    )
                )

        }else{
            return ItemRecyclerViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_chose_another_course,
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: ItemRecyclerViewHolder, position: Int) {
        val dashboardObject = list[position]
          holder.title.text = dashboardObject.title
        if (isUrduMedium) {

        }
        if (!isDashboard){
            holder.support = holder.itemView.findViewById(R.id.iv_contact_support)
            if (position == 4){
                holder.support.visibility=View.VISIBLE
                holder.title.setTextColor(context.resources.getColor(R.color.green))
            } else holder.support.visibility=View.GONE
        }
        holder.cardView.setOnClickListener {
            listener.onItemClick(dashboardObject,position)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

}