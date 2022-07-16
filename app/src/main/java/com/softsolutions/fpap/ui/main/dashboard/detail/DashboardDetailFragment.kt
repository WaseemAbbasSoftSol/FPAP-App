package com.softsolutions.fpap.ui.main.dashboard.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Html
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentDashboardDetailBinding
import com.softsolutions.fpap.databinding.FragmentDashboardDetailNewBinding
import com.softsolutions.fpap.databinding.FragmentDashboardDetailNewBindingImpl
import com.softsolutions.fpap.model.SubjectList
import com.softsolutions.fpap.ui.common.OnListItemClickListener
import com.softsolutions.fpap.ui.common.isUrduMedium
import com.softsolutions.fpap.utils.ChromeWebView
import com.softsolutions.fpap.utils.setWebView
import kotlinx.android.synthetic.main.layout_start_test_bottom.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardDetailFragment : Fragment(), OnListItemClickListener<SubjectList> {
    private lateinit var binding: FragmentDashboardDetailNewBinding
    private val mViewModel: DashboardDetailViewModel by viewModel()
    private var imageLink = "https://ikddata.ilmkidunya.com/images/subjectimages/"
    private var testId = 0
    private var unitId = 0
    companion object {
        var subjectId = 0
        var subjectName = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            val args = DashboardDetailFragmentArgs.fromBundle(it!!)
            mViewModel.subjectId = args.subjectId
            subjectId= args.subjectId
            subjectName=args.subjectName
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardDetailNewBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        if (isUrduMedium) {
            binding.back.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_back_right))
        }
        binding.bottomLayout.btn_start_test.setOnClickListener {
           goToMcqsFragment()
        }
        binding.bottomLayoutPost.btn_start_test.setOnClickListener {
            goToMcqsFragment()
        }
        binding.resultLayout.btnTestAgain.setOnClickListener {
            goToMcqsFragment()
        }
        binding.appbar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange

                }
                if (scrollRange + verticalOffset == 0) {
                    binding.tvHeader.visibility=View.GONE
                    binding.tvToolbar.visibility=View.VISIBLE
                  //  binding.clProgress.fadeVisibility(View.VISIBLE,4000)
                    isShow = true
                } else if (isShow) {
                    binding.tvHeader.visibility=View.VISIBLE
                    binding.tvToolbar.visibility=View.GONE
                  //  binding.clProgress.fadeVisibility(View.GONE,500)
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=mViewModel
        mViewModel.dashboardData.observe(viewLifecycleOwner){
            if (it!=null){
                binding.bottomLayout.visibility=View.VISIBLE
                testId=it.testId
                unitId=it.unitId
                binding.tvToolbar.text=subjectName
                binding.tvHeader.text=subjectName
                imageLink += it.subjectImage

                binding.tvDetail.text = getHtmlText(it.introContent)

                Glide.with(requireContext()).load(imageLink).into(binding.guideDetailImage)
                binding.bottomLayoutPost.tv_step1.text = getString(R.string.label_step2)
                binding.bottomLayoutPost.tv_pre_test.text = getString(R.string.label_post_test)
                binding.bottomLayoutPost.tv_start_test.text=getString(R.string.label_start_with_post_test)

                if (it.isSumbmittedPreTest){
                    binding.bottomLayout.btn_start_test.visibility=View.GONE
                    binding.bottomLayout.cl_inner.visibility=View.VISIBLE
                    binding.bottomLayout.tv_no_of_corrent_answer.text=it.preCorrectAns.toString()
                    binding.bottomLayout.tv_no_of_incorrent_answer.text=it.preIncorrectAns.toString()
                    binding.bottomLayout.tv_no_of_question.text=it.totalQuestions.toString()
                    binding.clPost.visibility=View.VISIBLE
                    binding.tvDetail2Post.text = getHtmlText(it.courseContent)
                    binding.tvDetailKeyMsg.text = getHtmlText(it.courseContent)
                    if (null!=it.video){
//                        binding.cvPost.visibility=View.VISIBLE
//                        setWebView(requireContext(),requireActivity(), binding.webviewPost)
//                        binding.webviewPost.webViewClient = Browser_home()
//                        binding.webviewPost.loadUrl(it.video)
                    }
                }

                if (it.isSubmittedPostTest){
                    binding.bottomLayoutPost.btn_start_test.visibility=View.GONE
                    binding.bottomLayoutPost.cl_inner.visibility=View.VISIBLE
                    binding.bottomLayoutPost.tv_no_of_corrent_answer.text=it.postCorrectAns.toString()
                    binding.bottomLayoutPost.tv_no_of_incorrent_answer.text=it.postIncorrectAns.toString()
                    binding.bottomLayoutPost.tv_no_of_question.text=it.totalQuestions.toString()
                   // binding.tvDetail2Post.text = getDescriptionText(it.courseContent)
                }
                if (it.isPassed) {
                    binding.resultLayout.root.visibility = View.VISIBLE
                    binding.resultLayout.tvChooseAnotherCourse.visibility = View.VISIBLE
                    binding.resultLayout.rvAnotherCourse.visibility = View.VISIBLE

                    val adapter = ChoseAnotherCourseAdapter(requireContext(), it.subjectList, this)
                    val layoutManager=GridLayoutManager(requireContext(),3)
                    binding.resultLayout.rvAnotherCourse.layoutManager=layoutManager
                    binding.resultLayout.rvAnotherCourse.adapter = adapter
                }
                else if (it.isFailed){
                    binding.resultLayout.root.visibility=View.VISIBLE
                    binding.resultLayout.btnTestAgain.visibility=View.VISIBLE
                    binding.resultLayout.tvStatus.text=getString(R.string.label_test_failed)
                    binding.resultLayout.tvCongo.text = getString(R.string.label_ops)
                    binding.resultLayout.tvDesc.text = getString(R.string.label_desc_failed)
                }
            }
        }
    }

    private fun goToMcqsFragment() = findNavController().navigate(
        DashboardDetailFragmentDirections.actionDashboardDetailToMcqsFragment(
            testId,
            unitId
        )
    )

    private fun getHtmlText(description: String): CharSequence {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT).trim()
        } else {
            HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT).trim()
        }
    }



    inner class Browser_home : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        //    binding.progressbar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
         //  binding.progressbar.visibility = View.GONE
         //   binding.webviewPost.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()");
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            // Toast.makeText(requireContext(), "Error:$error", Toast.LENGTH_SHORT).show()
            // progressDialog.dismiss()
        }
    }

    override fun onItemClick(item: SubjectList, pos: Int) {
        Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
    }

    fun View.fadeVisibility(visibility: Int, duration: Long = 400) {
        val transition: Transition = Fade()
        transition.duration = duration
        transition.addTarget(this)
        TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
        this.visibility = visibility
    }

}