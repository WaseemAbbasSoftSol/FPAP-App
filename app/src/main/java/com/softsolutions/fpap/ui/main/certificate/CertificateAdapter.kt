package com.softsolutions.fpap.ui.main.certificate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softsolutions.fpap.R
import com.softsolutions.fpap.model.CourseCertificate
import java.security.cert.Certificate

class CertificateAdapter(
    val context: Context,
    private val list: List<CourseCertificate>

) :
    RecyclerView.Adapter<CertificateAdapter.ItemRecyclerViewHolder>() {

    class ItemRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvEnglishTitle=itemView.findViewById<TextView>(R.id.tv_name_eng)
    val tvstatus=itemView.findViewById<TextView>(R.id.tv_pre_test)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRecyclerViewHolder {
            return ItemRecyclerViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_course_certificate,
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: ItemRecyclerViewHolder, position: Int) {
        val item=list[position]
        holder.tvEnglishTitle.text=item.subjectName
        holder.tvstatus.text=item.status
    }
    override fun getItemCount(): Int {
        return list.size
    }

}