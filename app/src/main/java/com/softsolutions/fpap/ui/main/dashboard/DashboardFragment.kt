package com.softsolutions.fpap.ui.main.dashboard

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentDashboardBinding
import com.softsolutions.fpap.model.Dashboard
import com.softsolutions.fpap.ui.account.SignoutDialog
import com.softsolutions.fpap.ui.common.OnListItemClickListener
import kotlinx.android.synthetic.main.base_toolbar.view.*
import kotlinx.android.synthetic.main.item_dashboard.view.*

class DashboardFragment:Fragment(),OnListItemClickListener<Dashboard> {
    private lateinit var binding:FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
      binding.appbar.toolbar.tv_toolbar.text="LSBE Online Course"
        binding.appbar.toolbar.back.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_ham_menu))
        binding.appbar.toolbar.back.setOnClickListener {
            binding.drawerlayout.openDrawer(GravityCompat.START)
        }
        val list= arrayListOf<Dashboard>()
        for (i in 0..7){
            list.add(Dashboard("","subject $i"))
        }
        val adapter=DashboardAdapter(requireContext(), list,this,true)
        val layoutManager=GridLayoutManager(requireContext(), 3)
        binding.appbar.rvDashboard.layoutManager=layoutManager
        binding.appbar.rvDashboard.adapter=adapter

        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> ""
                R.id.certificate->findNavController().navigate(DashboardFragmentDirections.actionDashboardCertificateFragment())
                R.id.profile->findNavController().navigate(DashboardFragmentDirections.actionDashboardToProfileFragmet())
                R.id.signout->{
                    val dialog=SignoutDialog()
                    dialog.show(requireActivity().supportFragmentManager, "")
                }

            }
            binding.drawerlayout.closeDrawer(GravityCompat.START)
            true
        }

        return binding.root
    }

    override fun onItemClick(item: Dashboard, pos: Int) {
        findNavController().navigate(DashboardFragmentDirections.actionDashboardToDashboardDetailFragment())
    }

}