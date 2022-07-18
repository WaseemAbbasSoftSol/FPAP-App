package com.softsolutions.fpap.ui.main.certificate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softsolutions.fpap.R
import com.softsolutions.fpap.model.CourseCertificate
import com.softsolutions.fpap.ui.common.isUrduMedium
import com.softsolutions.fpap.utils.splitDateAndTime
import java.security.cert.Certificate

class CertificateAdapter(
    val context: Context,
    private val list: List<CourseCertificate>

) :
    RecyclerView.Adapter<CertificateAdapter.ItemRecyclerViewHolder>() {

    class ItemRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvsubjectName=itemView.findViewById<TextView>(R.id.tv_sub_name)
    val tvstatus=itemView.findViewById<TextView>(R.id.tv_status)
    val date=itemView.findViewById<TextView>(R.id.tv_date)
    val download=itemView.findViewById<TextView>(R.id.tv_download)
        val view=itemView.findViewById<View>(R.id.view2)

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

    override fun onBindViewHolder(holder: ItemRecyclerViewHolder, position: Int) {
        val item=list[position]
        if (isUrduMedium){
            holder.tvsubjectName.text=item.urduName
            if (item.status=="Not Attempt"){
                holder.tvstatus.text="ٹیسٹ نہیں دیا"
            }

        }
        else{
            holder.tvsubjectName.text=item.subjectName
            holder.tvstatus.text=item.status
        }

        if (item.status!="Not Attempt"){
            holder.date.visibility=View.VISIBLE
            holder.view.visibility=View.VISIBLE
            val dd=splitDateAndTime(item.date)
            holder.date.text=dd
        }

        if (item.file != null){
            holder.download.visibility=View.VISIBLE
//            holder.tvcertificate.visibility=View.VISIBLE
        }
        holder.download.setOnClickListener {
            if (item.file!=null){
                //download data
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

}