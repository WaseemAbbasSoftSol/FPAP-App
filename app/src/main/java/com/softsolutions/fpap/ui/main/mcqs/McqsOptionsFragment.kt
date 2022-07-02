package com.softsolutions.fpap.ui.main.mcqs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.softsolutions.fpap.databinding.FragmentMcqsOptionsBinding

class McqsOptionsFragment:Fragment() {
    private lateinit var binding:FragmentMcqsOptionsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMcqsOptionsBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this

        val adapter=McqsOptionAdapter(requireContext())
        binding.rvMcqs.adapter=adapter

        return binding.root
    }
}