package com.lmslsbe.ui.main.mcqs

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lmslsbe.R
import com.lmslsbe.databinding.FragmentMcqsMainContainerBinding
import com.lmslsbe.model.mcq.Mcq
import com.lmslsbe.model.mcq.McqsOption
import com.lmslsbe.model.mcq.SubmitMcq
import com.lmslsbe.ui.common.SubmitDialog
import com.lmslsbe.ui.common.isUrduMedium
import com.lmslsbe.ui.common.mcqSubmittedAndShowResultAtBottom
import com.lmslsbe.ui.main.dashboard.DashboardFragment.Companion.courseName
import com.lmslsbe.ui.main.dashboard.detail.DashboardDetailFragment
import com.lmslsbe.utils.exitFullScreenMode
import com.lmslsbe.utils.makeProgressOnButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class McqsContainerFragment:Fragment() {
    private lateinit var binding: FragmentMcqsMainContainerBinding
    private val mViewModel: McqsViewModel by viewModel()
    private var mcqList = arrayListOf<Mcq>()
    private var currentIndex = 0
    private var totalQuestion = 0


    private var attemptedMcqsList = arrayListOf<SubmitMcq>()

    private var q = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            val args = McqsContainerFragmentArgs.fromBundle(it!!)
            mViewModel.unitId=args.unitId
            mViewModel.testId=args.testId

        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMcqsMainContainerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        requireActivity().exitFullScreenMode()
        binding.toolbarLayout.tvToolbar.text=courseName
        if (isUrduMedium) {
            binding.toolbarLayout.back.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_back_right
                )
            )
            binding.ivPre.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_mcq_next
                )
            )
            binding.ivNext.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_mcq_pre
                )
            )
        }
        attemptedMcqsList.clear()
        binding.ivNext.setOnClickListener {
            ++currentIndex
            if (isUrduMedium) setQuestion(
                mcqList[currentIndex],
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            else setQuestion(mcqList[currentIndex], R.anim.slide_in_right, R.anim.slide_out_left)
            previewButton()
        }
        binding.ivPre.setOnClickListener {
            --currentIndex
            if (isUrduMedium) setQuestion(
                mcqList[currentIndex],
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            else setQuestion(mcqList[currentIndex], R.anim.slide_in_left, R.anim.slide_out_right)
            previewButton()
        }

        binding.btnSubmit.setOnClickListener {
            val dialog = SubmitDialog()
            dialog.show(requireActivity().supportFragmentManager, "")
            dialog.setDialogPositiveClick(object :
                SubmitDialog.OnDialogPositiveButtonClickListener {
                override fun onyesButtonClik(isClicked: Boolean) {
                    makeProgressOnButton(binding.btnSubmit,R.string.plz_wait)
                    if (isClicked) mViewModel.submitMcqsList(attemptedMcqsList)
                }
            })
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
                binding.cl.visibility=View.VISIBLE
            }
        }
        mViewModel.mcqSubmittedMsg.observe(viewLifecycleOwner){
            if (it=="Certificate Detail found"){
                findNavController().navigate(McqsContainerFragmentDirections.actionMcqToDashboardDetailFragment(
                    DashboardDetailFragment.subjectId,
                DashboardDetailFragment.subjectName))
                mcqSubmittedAndShowResultAtBottom =true
            }
        }

    }

    private fun setQuestion(mcq : Mcq?, animEnter: Int, animExit: Int) {
        val bundle = Bundle()
        bundle.putSerializable("answer", mcq)
        bundle.putInt("questionNo", currentIndex)
        bundle.putString("question", mcq!!.questionText)

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
            override fun onOptionSelected(
                position: Int,
                item: McqsOption,
                questionId: Int,
                testId: Int,
                isAnySelected:Boolean
            ) {
                val submitMcq = SubmitMcq(mViewModel.memberId, testId, questionId, item.id)
                if (isAnySelected){
                   /* val toast: Toast = Toast.makeText(context, R.string.cant_select_multiple_option, Toast.LENGTH_SHORT)
                    val toastLayout = toast.view as LinearLayout?
                    val toastTV = toastLayout!!.getChildAt(0) as TextView
                    val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_regular)
                    toastTV.typeface = typeface
                    //toast.show()
                    return*/

                    val iterator = attemptedMcqsList.iterator()
                    for(i in iterator){
                        if(i.questionId== submitMcq.questionId){
                            iterator.remove()
                        }
                    }
                }

                attemptedMcqsList.add(submitMcq)
            }
        })
    }

    private fun previewButton() {
        binding.ivPre.visibility=if (currentIndex>=1) View.VISIBLE else View.INVISIBLE
        binding.ivNext.visibility =
            if (currentIndex < mcqList.size - 1) View.VISIBLE else View.INVISIBLE
        binding.btnSubmit.visibility =
            if (currentIndex == mcqList.size - 1) View.VISIBLE else View.GONE
    }
}