package com.softsolutions.fpap.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentResultBinding
import com.softsolutions.fpap.model.Dashboard
import com.softsolutions.fpap.ui.common.OnListItemClickListener
import com.softsolutions.fpap.ui.isUrduMedium
import com.softsolutions.fpap.ui.main.dashboard.DashboardAdapter
import com.softsolutions.fpap.utils.setTextViewFont
import kotlinx.android.synthetic.main.base_toolbar.view.*

class ResultFragment:Fragment(),OnListItemClickListener<Dashboard> {
    private lateinit var binding:FragmentResultBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentResultBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        if (isUrduMedium){
            setTextViewFont(binding.tvStatus, R.font.alvi_nastaleeq_regular,requireContext(),30)
            setTextViewFont(binding.tvCongo, R.font.alvi_nastaleeq_regular,requireContext(),26)
            setTextViewFont(binding.tvDesc, R.font.alvi_nastaleeq_regular,requireContext(),20)
            setTextViewFont(binding.btnDownload, R.font.alvi_nastaleeq_regular,requireContext())
            setTextViewFont(binding.tvChooseAnotherCourse, R.font.alvi_nastaleeq_regular,requireContext(),26)
            setTextViewFont(binding.toolbarLayout.tv_toolbar, R.font.alvi_nastaleeq_regular,requireContext(),26)
            binding.toolbarLayout.back.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_back_right))
        }
        val list= arrayListOf<Dashboard>()
        for (i in 0..7){
            val sub=resources.getString(R.string.label_human_rights)
            list.add(Dashboard("", "$sub $i"))
        }
     val layoutManager=GridLayoutManager(requireContext(),3)
        val adapter=DashboardAdapter(requireContext(), list,this,false, isUrduMedium)
        binding.rvAnotherCourse.layoutManager=layoutManager
        binding.rvAnotherCourse.adapter=adapter
        return binding.root
    }

    override fun onItemClick(item: Dashboard, pos: Int) {

    }
}