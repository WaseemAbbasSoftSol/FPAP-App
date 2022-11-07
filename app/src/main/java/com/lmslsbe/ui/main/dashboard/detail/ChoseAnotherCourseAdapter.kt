package com.lmslsbe.ui.main.dashboard.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lmslsbe.R
import com.lmslsbe.model.SubjectList
import com.lmslsbe.ui.common.OnListItemClickListener
import com.lmslsbe.ui.common.shardCourseName
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