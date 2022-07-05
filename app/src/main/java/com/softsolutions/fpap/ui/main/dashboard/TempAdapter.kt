package com.softsolutions.fpap.ui.main.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsolutions.fpap.model.Dashboard
import com.softsolutions.fpap.R
import com.softsolutions.fpap.model.SubjectList
import com.softsolutions.fpap.ui.common.OnListItemClickListener
import com.softsolutions.fpap.utils.setTextViewFont
import de.hdodenhof.circleimageview.CircleImageView

class TempAdapter(
    val context: Context,
    private val list: List<SubjectList>,
    private val listener:OnListItemClickListener<SubjectList>,
) :
    RecyclerView.Adapter<TempAdapter.ItemRecyclerViewHolder>() {

    class ItemRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_title)
       // var cardView:CardView= itemView.findViewById(R.id.cardview)
        val image:ImageView=itemView.findViewById(R.id.iv_dashboard)
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
        holder.title.text = dashboardObject.name
        val imageLink="https://ikddata.ilmkidunya.com/images/subjectimages/${dashboardObject.name}.png"

       // Glide.with(context).load(imageLink).into(holder.image)
        holder.itemView.setOnClickListener {
            listener.onItemClick(dashboardObject,position)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

}