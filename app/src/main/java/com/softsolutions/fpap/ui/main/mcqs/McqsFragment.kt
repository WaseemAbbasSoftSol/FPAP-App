package com.softsolutions.fpap.ui.main.mcqs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolutions.fpap.databinding.FragmentMcqsBinding
import kotlinx.android.synthetic.main.base_toolbar.view.*

class McqsFragment:Fragment() {
    private lateinit var binding:FragmentMcqsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMcqsBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.toolbarLayout.tv_toolbar.text="MCQS Set 01"
       binding.btnSubmit.setOnClickListener {
           findNavController().navigate(McqsFragmentDirections.actionMcqFragmentToResultFragment())
       }
        return binding.root
    }
}