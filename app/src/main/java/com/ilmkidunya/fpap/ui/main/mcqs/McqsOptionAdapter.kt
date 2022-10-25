package com.ilmkidunya.fpap.ui.main.mcqs

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ilmkidunya.fpap.R
import com.ilmkidunya.fpap.model.mcq.Mcq
import com.ilmkidunya.fpap.model.mcq.McqsOption

class McqsOptionAdapter(
    val context: Context,
    private val options: List<McqsOption>,
    private val listener: OnMcqsOptionClickListener,
    private val mcq:Mcq
) :
    RecyclerView.Adapter<McqsOptionAdapter.ItemRecyclerViewHolder>() {
     private var currentSelectedPosition = -1
    private var correctPosition=-1
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
        holder.tvq.text = item.answerText
        holder.tvoption.setOption(position)
        holder.itemView.setOnClickListener {
            currentSelectedPosition = position
            mcq.userSelectedPos=position
            listener.onOptionClick(position, item)
            mcq.isOp=mcq.isOp+1
            for ((i,value ) in options.withIndex()){
                if (value.isCorrect){
                    correctPosition=i
                    break
                }
            }
            notifyDataSetChanged()
        }

        if (mcq.isOp==1){//mcq.isOp is number of selection of an mcq. user can select one option only at one time.
            if (currentSelectedPosition == position) {
                holder.tvoption.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.tvq.setTextColor(ContextCompat.getColor(context, R.color.white))
              //  if (item.isCorrect) {
                    holder.cardView.setBackgroundResource(R.color.green)
              //  }
//                else{
//                    holder.cardView.setBackgroundResource(R.color.green)
//                   mcq.wrongSelectedPosition=position
//                    mcq.isWrongSelected=true
//                }
            }
//            else if (correctPosition==position){
//                holder.cardView.setBackgroundResource(R.color.green)
//                holder.tvoption.setTextColor(ContextCompat.getColor(context, R.color.white))
//                holder.tvq.setTextColor(ContextCompat.getColor(context, R.color.white))
//            }

            else{
                holder.cardView.setBackgroundResource(R.color.grey_001)
                holder.tvoption.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.tvq.setTextColor(ContextCompat.getColor(context, R.color.black))
            }
        }
            if (isAnySelected()){
                if (mcq.userSelectedPos==position){
                    holder.cardView.setBackgroundResource(R.color.green)
                    holder.tvoption.setTextColor(ContextCompat.getColor(context, R.color.white))
                    holder.tvq.setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                else{
                    holder.cardView.setBackgroundResource(R.color.grey_001)
                    holder.tvoption.setTextColor(ContextCompat.getColor(context, R.color.black))
                    holder.tvq.setTextColor(ContextCompat.getColor(context, R.color.black))
                }
//                if (item.isCorrect) {
//                    holder.cardView.setBackgroundResource(R.color.green)
//                    holder.tvoption.setTextColor(ContextCompat.getColor(context, R.color.white))
//                    holder.tvq.setTextColor(ContextCompat.getColor(context, R.color.white))
//                }
//                else if (mcq.wrongSelectedPosition==position && mcq.isWrongSelected){
//                    holder.tvoption.setTextColor(ContextCompat.getColor(context, R.color.white))
//                    holder.tvq.setTextColor(ContextCompat.getColor(context, R.color.white))
//                    holder.cardView.setBackgroundResource(R.color.red)
//                }

            }
    }

    override fun getItemCount(): Int {
        return options.size
    }

    interface OnMcqsOptionClickListener {
        fun onOptionClick(position: Int, item: McqsOption)
    }

    private fun isAnySelected(): Boolean {
            if (mcq.isAnySelected) return true
        return false
    }

    private fun TextView.setOption(position: Int){
        when (position) {
            0 -> this.text = context.getString(R.string.opn_a)
            1 -> this.text = context.getString(R.string.opn_b)
            2 -> this.text = context.getString(R.string.opn_c)
            else -> this.text = context.getString(R.string.opn_d)
        }
    }
}