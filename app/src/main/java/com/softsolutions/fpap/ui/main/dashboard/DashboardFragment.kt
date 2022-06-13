package com.softsolutions.fpap.ui.main.dashboard

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.softsolutions.fpap.CustomActivity
import com.softsolutions.fpap.R
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.databinding.FragmentDashboardBinding
import com.softsolutions.fpap.model.Dashboard
import com.softsolutions.fpap.ui.account.SignoutDialog
import com.softsolutions.fpap.ui.common.OnListItemClickListener
import com.softsolutions.fpap.ui.isUrduMedium
import com.softsolutions.fpap.ui.main.MainFragmentDirections
import com.softsolutions.fpap.utils.setTextViewFont
import kotlinx.android.synthetic.main.base_toolbar.view.*
import java.util.*


class DashboardFragment:Fragment(),OnListItemClickListener<Dashboard> {
    private lateinit var binding:FragmentDashboardBinding
    private lateinit var prefRepository: PrefRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        prefRepository= PrefRepository(requireActivity().application)
        if (isUrduMedium) setTextViewFont(binding.appbar.tvWelcome,R.font.alvi_nastaleeq_regular,requireContext(),22)
        val list= arrayListOf<Dashboard>()
        for (i in 0..7){
            val sub=resources.getString(R.string.dashboard_subject)
            list.add(Dashboard("","$sub $i"))
        }
        val adapter=DashboardAdapter(requireContext(), list,this,true, isUrduMedium)
        val layoutManager=GridLayoutManager(requireContext(), 3)
        binding.appbar.rvDashboard.layoutManager=layoutManager
        binding.appbar.rvDashboard.adapter=adapter

        return binding.root
    }

    override fun onItemClick(item: Dashboard, pos: Int) {
       findNavController().navigate(DashboardFragmentDirections.actionDashboardToDashboardDetailFragment())
    }

}