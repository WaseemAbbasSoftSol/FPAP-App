package com.softsolutions.fpap.ui.main.dashboard.detail

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentDashboardDetailNewBinding
import com.softsolutions.fpap.databinding.FragmentDashboardDetailNewUpdatedBinding
import com.softsolutions.fpap.model.SubjectList
import com.softsolutions.fpap.ui.common.OnListItemClickListener
import com.softsolutions.fpap.ui.common.isUrduMedium
import com.softsolutions.fpap.utils.APP_TAG
import com.softsolutions.fpap.utils.makeStatusBarTransparent
import kotlinx.android.synthetic.main.layout_start_test_bottom.view.*
import kotlinx.android.synthetic.main.layout_start_test_bottom.view.btn_start_test
import kotlinx.android.synthetic.main.layout_start_test_bottom.view.tv_no_of_question
import kotlinx.android.synthetic.main.layout_start_test_bottom.view.tv_pre_test
import kotlinx.android.synthetic.main.layout_start_test_bottom.view.tv_start_test
import kotlinx.android.synthetic.main.layout_start_test_pre_new_updated.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardDetailFragment : Fragment(), OnListItemClickListener<SubjectList> {
    private lateinit var binding: FragmentDashboardDetailNewUpdatedBinding
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
        binding= FragmentDashboardDetailNewUpdatedBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        requireActivity().makeStatusBarTransparent()
        if (isUrduMedium) {
            binding.back.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_back_right_white))
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

        binding.clVideo.setOnClickListener{
            binding.clContent.background=ContextCompat.getDrawable(requireContext(),R.drawable.ic_bg_grey)
            binding.tvContentTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.tvContentTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_content_black, 0, 0, 0)

            binding.clVideo.background=ContextCompat.getDrawable(requireContext(),R.drawable.bg_green)
            binding.tvVideoTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.tvVideoTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_video, 0, 0, 0)

            binding.viewpager.currentItem = 1
        }
        binding.clContent.setOnClickListener{
            binding.clVideo.background=ContextCompat.getDrawable(requireContext(),R.drawable.ic_bg_grey)
            binding.tvVideoTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.tvVideoTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_video_black, 0, 0, 0)

            binding.clContent.background=ContextCompat.getDrawable(requireContext(),R.drawable.bg_green)
            binding.tvContentTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.tvContentTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_content_white, 0, 0, 0)

            binding.viewpager.currentItem = 0
        }

        val pagerAdapter = activity?.let { ScreenSlidePagerAdapter(it) }
        binding.viewpager.adapter = pagerAdapter
        binding.viewpager.isUserInputEnabled = false

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
                imageLink = "https://ikddata.ilmkidunya.com/images/subjectimages/${it.subjectImage}"

                binding.tvDetail.text = getHtmlText(it.introContent)

                Glide.with(requireContext()).load(imageLink).into(binding.guideDetailImage)
                Log.d(APP_TAG,"ddd "+imageLink)
                binding.bottomLayoutPost.tv_step1.text = getString(R.string.label_step2)
                binding.bottomLayoutPost.tv_pre_test.text = getString(R.string.label_post_test)
                binding.bottomLayout.tv_no_of_question.text=it.totalQuestions.toString()

                binding.bottomLayout.tv_no_of_question.text=it.totalQuestions.toString()+" Questions"
                if (it.isSumbmittedPreTest){
                    binding.bottomLayout.l_first.visibility=View.GONE
                    binding.bottomLayout.l_second.visibility=View.VISIBLE
                    binding.bottomLayout.tv_no_of_questions.text=it.totalQuestions.toString()+" Questions"
                    binding.bottomLayout.tv_no_of_correct_answer.text=it.preCorrectAns.toString()
                    binding.bottomLayout.tv_no_of_incorrect_answer.text=it.preIncorrectAns.toString()

                    binding.clPost.visibility=View.VISIBLE
                  //  binding.tvDetail2Post.text = getHtmlText(it.courseContent)
                   // binding.tvDetailKeyMsg.text = getHtmlText(it.courseContent)
                    if (null!=it.video){
//                        binding.cvPost.visibility=View.VISIBLE
//                        setWebView(requireContext(),requireActivity(), binding.webviewPost)
//                        binding.webviewPost.webViewClient = Browser_home()
//                        binding.webviewPost.loadUrl(it.video)
                    }
                }else{
                  //  binding.bottomLayout.tv_start_test.text=getString(R.string.label_start_with_pre_test)
                }

                if (it.isSubmittedPostTest){
                    binding.bottomLayoutPost.tv_start_test.text=getString(R.string.label_start_with_post_test_result)
                    binding.bottomLayoutPost.btn_start_test.visibility=View.GONE
                    binding.bottomLayoutPost.cl_inner.visibility=View.VISIBLE
                    binding.bottomLayoutPost.tv_no_of_corrent_answer.text=it.postCorrectAns.toString()
                    binding.bottomLayoutPost.tv_no_of_incorrent_answer.text=it.postIncorrectAns.toString()
                    binding.bottomLayoutPost.tv_no_of_question.text=it.totalQuestions.toString()
                   // binding.tvDetail2Post.text = getDescriptionText(it.courseContent)
                }else{
                    binding.bottomLayoutPost.tv_start_test.text=getString(R.string.label_start_with_post_test)
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
        if (item.isPassed){
            val toast: Toast = Toast.makeText(context, R.string.test_already_taken, Toast.LENGTH_SHORT)
            val toastLayout = toast.view as LinearLayout?
            val toastTV = toastLayout!!.getChildAt(0) as TextView
            val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_regular)
            toastTV.typeface = typeface
            toast.show()
            return
        }
        val subjeName= if (!item.subName().isNullOrEmpty())item.subName() else ""
       findNavController().navigate(DashboardDetailFragmentDirections.actionDashboardDetailToDashboardDetailItself(item.id,subjeName))
    }

    fun View.fadeVisibility(visibility: Int, duration: Long = 400) {
        val transition: Transition = Fade()
        transition.duration = duration
        transition.addTarget(this)
        TransitionManager.beginDelayedTransition(this.parent as ViewGroup, transition)
        this.visibility = visibility
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ContentAndVideoSubFragment(true)
                1 -> ContentAndVideoSubFragment(false)
                else -> ContentAndVideoSubFragment(true)
            }
        }
    }

}