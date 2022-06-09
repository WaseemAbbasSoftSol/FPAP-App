package com.softsolutions.fpap.ui.main.dashboard.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.softsolutions.fpap.databinding.FragmentDashboardDetailBinding


class DashboardDetailFragment:Fragment() {
    private lateinit var binding:FragmentDashboardDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardDetailBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this

     binding.appbar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
         if(binding.collapsingToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(binding.collapsingToolbar)) {
             Toast.makeText(requireContext(),"collapsed", Toast.LENGTH_SHORT).show()
         } else {
             Toast.makeText(requireContext(),"ex", Toast.LENGTH_SHORT).show()
         }
     })

        return binding.root
    }
}