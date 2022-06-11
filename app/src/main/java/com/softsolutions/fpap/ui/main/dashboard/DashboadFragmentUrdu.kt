package com.softsolutions.fpap.ui.main.dashboard

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.softsolutions.fpap.R
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.databinding.FragmentDashboardBinding
import com.softsolutions.fpap.databinding.FragmentDashboardUrduBinding
import com.softsolutions.fpap.model.Dashboard
import com.softsolutions.fpap.ui.account.SignoutDialog
import com.softsolutions.fpap.ui.common.OnListItemClickListener
import kotlinx.android.synthetic.main.base_toolbar.view.*
import java.util.*


class DashboadFragmentUrdu: Fragment(), OnListItemClickListener<Dashboard> {
    private lateinit var binding: FragmentDashboardUrduBinding
    private lateinit var prefRepository: PrefRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardUrduBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        prefRepository= PrefRepository(requireActivity().application)

//        binding.appbar.toolbar.tv_toolbar.text=resources.getString(R.string.dashboard_toolbar)
//        binding.appbar.toolbar.back.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_ham_menu))
//        binding.appbar.toolbar.back.setOnClickListener {
//          //  binding.drawerlayout.openDrawer(GravityCompat.START)
//        }
        val list= arrayListOf<Dashboard>()
        for (i in 0..7){
            val sub=resources.getString(R.string.dashboard_subject_urdu)
            list.add(Dashboard("","$sub $i"))
        }
        val adapter=DashboardAdapter(requireContext(), list,this,true, true)
        val layoutManager= GridLayoutManager(requireContext(), 3)
        binding.appbar.rvDashboard.layoutManager=layoutManager
        binding.appbar.rvDashboard.adapter=adapter


        return binding.root
    }

    override fun onItemClick(item: Dashboard, pos: Int) {
      //  findNavController().navigate(DashboardFragmentDirections.actionDashboardToDashboardDetailFragment())
    }

    private fun setLocate(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        requireActivity().baseContext.resources.updateConfiguration(config, requireActivity().baseContext.resources.displayMetrics)
        val editor = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }
    private fun loadLocate() {
        val sharedPreferences = requireActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setLocate(language)
        }
    }
}