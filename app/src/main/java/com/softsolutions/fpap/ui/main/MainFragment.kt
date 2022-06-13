package com.softsolutions.fpap.ui.main

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.softsolutions.fpap.databinding.MainFragmentBinding
import com.softsolutions.fpap.ui.main.dashboard.DashboadFragmentUrdu
import com.softsolutions.fpap.ui.main.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.base_toolbar.view.*


class MainFragment:Fragment() {
    private lateinit var binding:MainFragmentBinding
    private var isUrdu=false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MainFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this

        val pagerAdapter = activity?.let { ScreenSlidePagerAdapter(it) }
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false

        return binding.root
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 5

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> DashboardFragment()
                1 -> DashboadFragmentUrdu()
                else -> DashboardFragment()
            }
        }
    }
//    private fun isUrduCheck(){
//        if (isUrdu){
//            binding.toolbar.tv_toolbar.text="ٹول بار"
//        }else binding.toolbar.tv_toolbar.text="Toolbar"
//    }
    fun changeFragmnt(position :Int){
        binding.viewPager.currentItem = position
}
}