package com.softsolutions.fpap.ui.main.dashboard.detail

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsolutions.fpap.R
import com.softsolutions.fpap.model.SubjectList
import com.softsolutions.fpap.ui.common.OnListItemClickListener
import de.hdodenhof.circleimageview.CircleImageView

class ChoseAnotherCourseAdapter(
    val context: Context,
    private val list: List<SubjectList>,
    private val listener: OnListItemClickListener<SubjectList>,
) :
    RecyclerView.Adapter<ChoseAnotherCourseAdapter.ItemRecyclerViewHolder>() {

    class ItemRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.tv_title)
        var iv: ImageView = itemView.findViewById(R.id.iv_dashboard)
        var cardView: CardView = itemView.findViewById(R.id.cardview)
        var support: CircleImageView=itemView.findViewById(R.id.iv_passed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecyclerViewHolder {
            return ItemRecyclerViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_choose_another_course,
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: ItemRecyclerViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.subName()
        Glide.with(context).load(item.image1).into(holder.iv)
        if (item.isPassed) {
            holder.support.visibility=View.VISIBLE
           // holder.cardView.background.setTint(ContextCompat.getColor(context,R.color.purple))
           // holder.cardView.foreground=context.resources.getDrawable(R.drawable.cardview_solid_border_color_purple)
        }
        holder.cardView.setOnClickListener {
            listener.onItemClick(item,position)
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

}