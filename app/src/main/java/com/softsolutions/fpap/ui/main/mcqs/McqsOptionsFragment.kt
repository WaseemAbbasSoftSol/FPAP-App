package com.softsolutions.fpap.ui.main.mcqs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.softsolutions.fpap.databinding.FragmentMcqsOptionsBinding
import com.softsolutions.fpap.model.mcq.Mcq
import com.softsolutions.fpap.model.mcq.McqsOption

class McqsOptionsFragment:Fragment(), McqsOptionAdapter.OnMcqsOptionClickListener {
    private lateinit var binding:FragmentMcqsOptionsBinding
    private var mcq: Mcq? = null
    private var qNo=0
    private var listener: OptionSelectedCallback? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMcqsOptionsBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this

        val bundle = this.arguments
        mcq = bundle!!.getSerializable("answer") as Mcq?
        qNo= bundle.getInt("questionNo")
        qNo += 1
        binding.tvQuestionNo.text = "Question $qNo"
        //val adapter=McqsOptionAdapter(requireContext(),mcq!!.answer, this)
        val adapter=McqsOptionAdapter(requireContext(),mcq!!.answer, this)
        binding.rvMcqs.adapter=adapter

        return binding.root
    }

    override fun onOptionClick(position: Int, item: McqsOption) {
        listener!!.onOptionSelected(position, item, mcq!!.questionId,mcq!!.testId)
    }

    interface OptionSelectedCallback{
        fun onOptionSelected(position: Int, item: McqsOption, questionId:Int, testId:Int)
    }

    fun setOptionClickedCallback(listener: OptionSelectedCallback) {
        this.listener = listener
    }
}