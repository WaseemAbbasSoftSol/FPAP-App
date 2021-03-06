package com.softsolutions.fpap.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.softsolutions.fpap.databinding.FragmentResultBinding
import com.softsolutions.fpap.model.Dashboard
import com.softsolutions.fpap.ui.common.OnListItemClickListener
import com.softsolutions.fpap.ui.main.dashboard.DashboardAdapter
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
        binding.toolbarLayout.tv_toolbar.text="Interpersonal Relationships"
        val list= arrayListOf<Dashboard>()
        for (i in 0..7){
            list.add(Dashboard("", "subject $i"))
        }
     val layoutManager=GridLayoutManager(requireContext(),3)
        val adapter=DashboardAdapter(requireContext(), list,this,false)
        binding.rvAnotherCourse.layoutManager=layoutManager
        binding.rvAnotherCourse.adapter=adapter
        return binding.root
    }

    override fun onItemClick(item: Dashboard, pos: Int) {

    }
}