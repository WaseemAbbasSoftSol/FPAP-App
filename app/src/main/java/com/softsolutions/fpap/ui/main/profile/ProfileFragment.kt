package com.softsolutions.fpap.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentProfileBinding
import com.softsolutions.fpap.ui.isUrduMedium
import com.softsolutions.fpap.utils.setTextViewFont
import kotlinx.android.synthetic.main.base_toolbar.view.*

class ProfileFragment:Fragment() {
   private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        binding.toolbarLayout.tv_toolbar.text="Profile"
        binding.lifecycleOwner=this

        return binding.root
    }
}