package com.softsolutions.fpap.ui.main

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
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.MainFragmentBinding
import com.softsolutions.fpap.ui.account.SignoutDialog
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


              binding.toolbar.tv_toolbar.text=resources.getString(R.string.dashboard_toolbar)
        binding.toolbar.back.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_ham_menu))
        binding.toolbar.back.setOnClickListener {
            binding.drawerlayout.openDrawer(GravityCompat.START)
        }

        val headerView = binding.navigationView.inflateHeaderView(R.layout.header_layout)
        val switchCompat=headerView.findViewById<SwitchCompat>(R.id.sth_med)

        val pagerAdapter = activity?.let { ScreenSlidePagerAdapter(it) }

        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false

        switchCompat.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                isUrdu=true
                isUrduCheck()
                binding.viewPager.currentItem = 1
               // Toast.makeText(requireContext(),"main",Toast.LENGTH_SHORT).show()
            }
            else {
                isUrdu=false
                isUrduCheck()
               binding.viewPager.currentItem = 0
               // Toast.makeText(requireContext(),"main",Toast.LENGTH_SHORT).show()

            }
        })

        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> ""
                R.id.certificate->findNavController().navigate(MainFragmentDirections.actionDashboardCertificateFragment())
                R.id.profile->findNavController().navigate(MainFragmentDirections.actionDashboardToProfileFragmet())
                R.id.signout->{
                    val dialog= SignoutDialog()
                    dialog.show(requireActivity().supportFragmentManager, "")
                }

            }
            binding.drawerlayout.closeDrawer(GravityCompat.START)
            true
        }
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
    private fun isUrduCheck(){
        if (isUrdu){
            binding.toolbar.tv_toolbar.text="ٹول بار"
        }else binding.toolbar.tv_toolbar.text="Toolbar"
    }
}