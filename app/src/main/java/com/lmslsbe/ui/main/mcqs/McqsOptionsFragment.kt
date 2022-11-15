package com.lmslsbe.ui.main.mcqs

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lmslsbe.R
import com.lmslsbe.databinding.FragmentMcqsOptionsBinding
import com.lmslsbe.model.mcq.Mcq
import com.lmslsbe.model.mcq.McqsOption

class McqsOptionsFragment:Fragment(), McqsOptionAdapter.OnMcqsOptionClickListener {
    private lateinit var binding: FragmentMcqsOptionsBinding
    private var mcq: Mcq? = null
    private var qNo=0
    private var questionText=""
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
        questionText= bundle.getString("question").toString()
        qNo += 1
        val questionNo= getString(R.string.question)
        binding.tvQuestionNo.text = "$questionNo $qNo"
       // binding.tvQuestion.text=questionText
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           binding.tvQuestion.setText(Html.fromHtml(questionText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            binding.tvQuestion.setText(Html.fromHtml(questionText));
        }
        //val adapter=McqsOptionAdapter(requireContext(),mcq!!.answer, this)
        val answer= arrayListOf<McqsOption>()
        for ((i,value) in mcq!!.answer.withIndex()){
            if (value.answerText.isNotEmpty()){
                answer.add(value)
            }
        }
        val adapter= McqsOptionAdapter(requireContext(),answer, this,mcq!!)
        binding.rvMcqs.adapter=adapter

        return binding.root
    }

    override fun onOptionClick(position: Int, item: McqsOption) {
        listener!!.onOptionSelected(position, item, mcq!!.questionId,mcq!!.testId, mcq!!.isAnySelected)
        mcq!!.isAnySelected=true
    }

    interface OptionSelectedCallback{
        fun onOptionSelected(position: Int, item: McqsOption, questionId:Int, testId:Int, isAnySelected:Boolean)
    }

    fun setOptionClickedCallback(listener: OptionSelectedCallback) {
        this.listener = listener
    }
}