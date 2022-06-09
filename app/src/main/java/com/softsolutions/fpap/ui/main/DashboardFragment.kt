package com.softsolutions.fpap.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentDashboardBinding
import kotlinx.android.synthetic.main.base_toolbar.view.*

class DashboardFragment:Fragment() {
    private lateinit var binding:FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this

        binding.appbar.toolbar.back.setImageDrawable(resources.getDrawable(R.drawable.ic_ham_menu))
        binding.appbar.toolbar.back.setOnClickListener {
            binding.drawerlayout.openDrawer(GravityCompat.START)
        }
        return binding.root
    }
}