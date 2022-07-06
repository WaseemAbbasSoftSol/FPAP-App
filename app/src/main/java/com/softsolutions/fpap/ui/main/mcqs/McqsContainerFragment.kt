package com.softsolutions.fpap.ui.main.mcqs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentMcqsMainContainerBinding
import com.softsolutions.fpap.model.mcq.Mcq
import com.softsolutions.fpap.model.mcq.McqsOption
import com.softsolutions.fpap.ui.common.isUrduMedium
import org.koin.androidx.viewmodel.ext.android.viewModel

class McqsContainerFragment:Fragment() {
    private lateinit var binding:FragmentMcqsMainContainerBinding
    private val mViewModel:McqsViewModel by viewModel()
    private var mcqList = arrayListOf<Mcq>()
    private var currentIndex=0
    private var totalQuestion=0

    private var q=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMcqsMainContainerBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this

        binding.ivNext.setOnClickListener{
            ++currentIndex
            if (isUrduMedium)setQuestion(mcqList[currentIndex],R.anim.slide_in_left, R.anim.slide_out_right)
            else setQuestion(mcqList[currentIndex],R.anim.slide_in_right, R.anim.slide_out_left)
            previewButton()
        }
        binding.ivPre.setOnClickListener {
            --currentIndex
            if (isUrduMedium) setQuestion(mcqList[currentIndex],R.anim.slide_in_right, R.anim.slide_out_left)
            else setQuestion(mcqList[currentIndex],R.anim.slide_in_left, R.anim.slide_out_right)
            previewButton()
        }
        if (isUrduMedium){
            binding.toolbarLayout.back.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_right))
            binding.ivPre.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_mcq_next))
            binding.ivNext.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_mcq_pre))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.mcq.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                totalQuestion=it.size
                mcqList.addAll(it)
                setQuestion(mcqList[0], 0,0)
                previewButton()

            }
        }


    }

    private fun setQuestion(mcq : Mcq?, animEnter: Int, animExit: Int) {
        val bundle = Bundle()
        bundle.putSerializable("answer", mcq)
        bundle.putInt("questionNo", currentIndex)

        val s=getString(R.string.no_of_mcqs)
        val nn=currentIndex+1
        binding.tvNoOfQuestion.text = "$nn $s $totalQuestion"

        val frag = McqsOptionsFragment()
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(animEnter, animExit)
        frag.arguments = bundle
        transaction?.replace(R.id.fl_mcqs_option_host, frag)
        transaction?.commit()
        frag.setOptionClickedCallback(object : McqsOptionsFragment.OptionSelectedCallback {
            override fun onOptionSelected(position: Int, item: McqsOption) {
                //  Toast.makeText(requireContext(),"finally ${item.isCorrect}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun previewButton() {
        binding.ivPre.visibility=if (currentIndex>=1) View.VISIBLE else View.INVISIBLE
        binding.ivNext.visibility=if (currentIndex<mcqList.size-1) View.VISIBLE else View.INVISIBLE

    }
}