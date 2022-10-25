package com.ilmkidunya.fpap.ui.main.certificate

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ilmkidunya.fpap.R
import com.ilmkidunya.fpap.model.CertificateDetail
import com.ilmkidunya.fpap.ui.common.isUrduMedium
import com.ilmkidunya.fpap.utils.splitDateAndTime

class CertificateAdapter(
    val context: Context,
    private val list: List<CertificateDetail>

) :
    RecyclerView.Adapter<CertificateAdapter.ItemRecyclerViewHolder>() {

    class ItemRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvsubjectName=itemView.findViewById<TextView>(R.id.tv_sub_name)
    val tvstatus=itemView.findViewById<TextView>(R.id.tv_status)
    val date=itemView.findViewById<TextView>(R.id.tv_date)
        val view=itemView.findViewById<View>(R.id.view2)
        val ll=itemView.findViewById<LinearLayout>(R.id.ll_sub_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecyclerViewHolder {
            return ItemRecyclerViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_course_certificate_new,
                    parent,
                    false
                )
            )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ItemRecyclerViewHolder, position: Int) {
        val item=list[position]
        if (isUrduMedium){
            holder.tvsubjectName.text=item.urduName
            if (item.status=="Not Attempted"){
                holder.tvstatus.text="ٹیسٹ نہیں دیا"
            }else if (item.status=="Passed"){
                holder.tvstatus.text="پاس"
            }else if (item.status=="In Progress"){
                holder.tvstatus.text="کام جاری ہے"
            }else if(item.status=="Failed"){
                holder.tvstatus.text="ناکام"
            }

        }
        else{
            if (item.status=="Not Attempted"){
                holder.tvstatus.text="Not given"
                holder.tvsubjectName.text=item.subjectName
            }else{
                holder.tvsubjectName.text=item.subjectName
                holder.tvstatus.text=item.status
            }

        }
        val dd=splitDateAndTime(item.date)
        if (item.status=="Passed"){
            holder.date.visibility=View.VISIBLE
            holder.view.visibility=View.VISIBLE

            holder.date.text=dd

        }else if (item.status=="Failed"){
            holder.tvstatus.setTextColor(context.getColor(R.color.red))
            holder.date.text=dd
            holder.date.visibility=View.VISIBLE
            holder.view.visibility=View.VISIBLE
        }else if (item.status=="In Progress" || item.status=="Not Attempt")holder.tvstatus.setTextColor(context.getColor(R.color.yellow))

        setBackgroundCol(holder.ll, position)

    }
    override fun getItemCount(): Int {
        return list.size
    }
@RequiresApi(Build.VERSION_CODES.M)
private fun setBackgroundCol(view:LinearLayout, position: Int){
    when(position){
        0-> view.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.yellow))
        1-> view.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.blue))
        2-> view.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.green_002))
        3-> view.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.red_001))
        4-> view.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.yellow))
        5-> view.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.blue))
        6-> view.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.green_002))
        7-> view.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.red_001))
        else->view.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.yellow))
    }

}
}