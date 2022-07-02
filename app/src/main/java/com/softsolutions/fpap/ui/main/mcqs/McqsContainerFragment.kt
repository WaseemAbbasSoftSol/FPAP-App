package com.softsolutions.fpap.ui.main.mcqs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.softsolutions.fpap.R
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.databinding.FragmentMcqsMainContainerBinding
import com.softsolutions.fpap.ui.common.isUrduMedium

class McqsContainerFragment:Fragment() {
    private lateinit var binding:FragmentMcqsMainContainerBinding
    private lateinit var prefRepository: PrefRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMcqsMainContainerBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        prefRepository= PrefRepository(requireActivity().application)
        setQuestion(0,0)
        binding.ivNext.setOnClickListener{
            if (isUrduMedium)setQuestion(R.anim.slide_in_left, R.anim.slide_out_right)
            else setQuestion(R.anim.slide_in_right, R.anim.slide_out_left,)
        }
        binding.ivPre.setOnClickListener {
            if (isUrduMedium) setQuestion(R.anim.slide_in_right, R.anim.slide_out_left)
            else setQuestion(R.anim.slide_in_left, R.anim.slide_out_right,)
        }
        if (isUrduMedium){
            binding.toolbarLayout.back.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_right))
            binding.ivPre.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_mcq_next))
            binding.ivNext.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_mcq_pre))
        }
        return binding.root
    }

    private fun setQuestion(animEnter: Int, animExit: Int) {
        val frag = McqsOptionsFragment()
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(animEnter, animExit)
        transaction?.replace(R.id.fl_mcqs_option_host, frag)
        transaction?.commit()
    }
}