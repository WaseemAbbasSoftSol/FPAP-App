package com.softsolutions.fpap.ui.main.mcqs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softsolutions.fpap.R
import com.softsolutions.fpap.model.mcq.McqsOption

class McqsOptionAdapter(
    val context: Context,
    private val options: List<McqsOption>,
    private val listener: OnMcqsOptionClickListener
) :
    RecyclerView.Adapter<McqsOptionAdapter.ItemRecyclerViewHolder>() {
private var backPosition=-1
    class ItemRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val tvoption=itemView.findViewById<TextView>(R.id.tv_option_a)
         val tvq=itemView.findViewById<TextView>(R.id.tv_quest_a)
        val cardView=itemView.findViewById<ConstraintLayout>(R.id.cardview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecyclerViewHolder {
            return ItemRecyclerViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_mcqs_options,
                    parent,
                    false
                )
            )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ItemRecyclerViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = options[position]
        holder.tvoption.text=item.answerText
        holder.itemView.setOnClickListener {
            backPosition=position
            listener.onOptionClick(position, item)
            notifyDataSetChanged()
        }

        if (backPosition==position){
            holder.tvoption.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.tvq.setTextColor(ContextCompat.getColor(context, R.color.white))
            if (item.isCorrect) holder.cardView.setBackgroundResource(R.color.green)
            else holder.cardView.setBackgroundResource(R.color.red)
        }else {
            holder.tvoption.setTextColor(ContextCompat.getColor(context, R.color.black))
            holder.tvq.setTextColor(ContextCompat.getColor(context, R.color.black))
            holder.cardView.setBackgroundResource(R.color.grey_001)
        }

    }

    override fun getItemCount(): Int {
        return options.size
    }

    interface OnMcqsOptionClickListener {
        fun onOptionClick(position: Int, item: McqsOption)
    }
}