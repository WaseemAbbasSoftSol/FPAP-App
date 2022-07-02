package com.softsolutions.fpap.ui.main.mcqs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentMcqsBinding
import com.softsolutions.fpap.ui.common.isUrduMedium
import com.softsolutions.fpap.utils.setTextViewFont
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
        if (isUrduMedium){
            binding.toolbarLayout.back.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_back_right))
        }
       binding.btnSubmit.setOnClickListener {
           findNavController().navigate(McqsFragmentDirections.actionMcqFragmentToResultFragment())
       }
        return binding.root
    }
}