package com.softsolutions.fpap.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.softsolutions.fpap.databinding.FragmentRecoverPasswordBinding
import kotlinx.android.synthetic.main.base_toolbar.view.*

class RecoverPasswordFragment: Fragment() {
    private lateinit var binding:FragmentRecoverPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRecoverPasswordBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.toolbar.tvToolbar.text="Password Recovery"
        return binding.root
    }
}