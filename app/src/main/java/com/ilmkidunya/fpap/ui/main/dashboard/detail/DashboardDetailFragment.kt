package com.ilmkidunya.fpap.ui.main.dashboard.detail

import android.content.Context
import android.graphics.Bitmap
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
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.ilmkidunya.fpap.R
import com.ilmkidunya.fpap.databinding.FragmentDashboardDetailNewUpdatedBinding
import com.ilmkidunya.fpap.model.SubjectList
import com.ilmkidunya.fpap.ui.common.OnListItemClickListener
import com.ilmkidunya.fpap.ui.common.isPostTest
import com.ilmkidunya.fpap.ui.common.isUrduMedium
import com.ilmkidunya.fpap.ui.common.mcqSubmittedAndShowResultAtBottom
import com.ilmkidunya.fpap.utils.*
import kotlinx.android.synthetic.main.layout_start_test_bottom.view.btn_start_test
import kotlinx.android.synthetic.main.layout_start_test_post_new_updated.view.*
import kotlinx.android.synthetic.main.layout_start_test_pre_new_updated.view.*
import kotlinx.android.synthetic.main.layout_start_test_pre_new_updated.view.l_first
import kotlinx.android.synthetic.main.layout_start_test_pre_new_updated.view.l_second
import kotlinx.android.synthetic.main.layout_start_test_pre_new_updated.view.tv_no_of_correct_answer
import kotlinx.android.synthetic.main.layout_start_test_pre_new_updated.view.tv_no_of_incorrect_answer
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardDetailFragment : Fragment(), OnListItemClickListener<SubjectList> {
    private lateinit var binding: FragmentDashboardDetailNewUpdatedBinding
    private val mViewModel: DashboardDetailViewModel by viewModel()
    private var imageLink = "https://ikddata.ilmkidunya.com/images/subjectimages/"
    private var testId = 0
    private var unitId = 0
    private var videoLink=""
    private var tempList= arrayListOf<SubjectList>()
    companion object {
        var subjectId = 0
        var subjectName = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate(requireActivity())
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
        loadLocate(requireActivity())
        binding= FragmentDashboardDetailNewUpdatedBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
      //  requireActivity().makeStatusBarTransparent()
        if (isUrduMedium) {
            binding.back.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_back_right_white))
        }
        setWebView(requireContext(),requireActivity(),binding.webViewLecture)
        binding.webViewLecture.webViewClient = Browser_home()
        binding.bottomLayout.btn_start_test.setOnClickListener {
           goToMcqsFragment()
        }
        binding.bottomLayoutPost.btn_start_test.setOnClickListener {
            isPostTest=true
            goToMcqsFragment()
        }
        binding.resultLayout.btnTestAgain.setOnClickListener {
            isPostTest=true
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
                    binding.tvHeader.visibility=View.VISIBLE
                    binding.tvToolbar.visibility=View.GONE
                  //  binding.clProgress.fadeVisibility(View.VISIBLE,4000)
                    isShow = true
                } else if (isShow) {
                    binding.tvHeader.visibility=View.GONE
                    binding.tvToolbar.visibility=View.VISIBLE
                  //  binding.clProgress.fadeVisibility(View.GONE,500)
                }
            }
        })

       /* binding.clVideo.setOnClickListener{
            binding.clContent.background=ContextCompat.getDrawable(requireContext(),R.drawable.ic_bg_grey)
            binding.tvContentTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.tvContentTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_content_black, 0, 0, 0)

            binding.clVideo.background=ContextCompat.getDrawable(requireContext(),R.drawable.bg_green)
            binding.tvVideoTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.tvVideoTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_video, 0, 0, 0)

            binding.nestedscroll.visibility=View.GONE
            binding.clVideos.visibility=View.VISIBLE
            binding.webViewLecture.onResume()

        }
        binding.clContent.setOnClickListener{
            binding.clVideo.background=ContextCompat.getDrawable(requireContext(),R.drawable.ic_bg_grey)
            binding.tvVideoTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.tvVideoTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_video_black, 0, 0, 0)

            binding.clContent.background=ContextCompat.getDrawable(requireContext(),R.drawable.bg_green)
            binding.tvContentTab.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.tvContentTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_content_white, 0, 0, 0)

            binding.nestedscroll.visibility=View.VISIBLE
            binding.clVideos.visibility=View.GONE
            binding.webViewLecture.onPause()

        }*/



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadLocate(requireActivity())
        binding.viewModel=mViewModel
        mViewModel.dashboardData.observe(viewLifecycleOwner){
            if (it!=null){
                tempList= it.subjectList as ArrayList<SubjectList>




                binding.bottomLayout.visibility=View.VISIBLE
                binding.guideDetailImage.visibility=View.VISIBLE
                testId=it.testId
                unitId=it.unitId
                binding.tvToolbar.text=subjectName
                binding.tvHeader.text=subjectName
                imageLink = "https://ikddata.ilmkidunya.com/images/subjectimages/${it.subjectImage}"

                binding.tvDetail.text = getHtmlText(it.introContent)

                Glide.with(requireContext()).load(imageLink).into(binding.guideDetailImage)
                Log.d(APP_TAG,"ddd "+imageLink)

                binding.bottomLayout.tv_no_of_question.text=it.totalQuestions.toString()+" "+getString(R.string.label_question)
                if (it.isSumbmittedPreTest){
                    binding.bottomLayout.l_first.visibility=View.GONE
                    binding.bottomLayout.l_second.visibility=View.VISIBLE
                    binding.bottomLayout.tv_no_of_questions.text=it.totalQuestions.toString()+" "+getString(R.string.label_question)
                    binding.bottomLayout.tv_no_of_correct_answer.text=it.preCorrectAns.toString()
                    binding.bottomLayout.tv_no_of_incorrect_answer.text=it.preIncorrectAns.toString()

                    binding.bottomLayoutPost.visibility=View.VISIBLE
                    binding.clPostNew.visibility=View.VISIBLE
                    setWebView(requireContext(),requireActivity(),binding.webView)
                    binding.webView.webViewClient = Browser_home()
                    binding.webView.loadUrl(it.courseContentLinkDetail)


                   // binding.tvDetails.text=getDescriptionText(it.courseContent)
//                    if (!it.video.isNullOrEmpty()){
//                        videoLink=it.video
//                        val embedcode = it.video
//                        val matcher = Pattern.compile("src=\"([^\"]+)\"").matcher(embedcode)
//                        matcher.find()
//                        var link = matcher.group(1)!!
//                        binding.webViewLecture.loadUrl(link)
//                    }
                }

                if (it.isSubmittedPostTest){
                    binding.bottomLayoutPost.btn_start_test.visibility=View.GONE


                }
                if (it.isPassed) {

                    if (mViewModel.gender=="Male                                              "){
                        for ((i,value )in tempList.withIndex()){
                            if (value.id==765){
                                tempList.remove(value)
                                break
                            }
                        }
                    }else{
                        for ((i,value )in tempList.withIndex()){
                            if (value.id==762){
                                tempList.remove(value)
                                break
                            }
                        }
                    }

                    binding.bottomLayoutPost.l_first.visibility=View.GONE
                    binding.bottomLayoutPost.l_second.visibility=View.VISIBLE
                    binding.bottomLayoutPost.tv_no_of_qs.text=it.totalQuestions.toString()
                    binding.bottomLayoutPost.tv_no_of_correct_answer.text=it.postCorrectAns.toString()
                    binding.bottomLayoutPost.tv_no_of_incorrect_answer.text=it.postIncorrectAns.toString()

                    binding.resultLayout.root.visibility = View.VISIBLE
                    binding.resultLayout.clAnotherCourse.visibility = View.VISIBLE

                    val adapter = ChoseAnotherCourseAdapter(requireContext(), tempList, this)
                    val layoutManager=GridLayoutManager(requireContext(),3)
                    binding.resultLayout.rvAnotherCourse.layoutManager=layoutManager
                    binding.resultLayout.rvAnotherCourse.adapter = adapter

                    if (isUrduMedium){
                        binding.resultLayout.tvCongo.text="مبارک ہو"
                        binding.resultLayout.tvStatus.text="امتحان پاس کیا"
                        binding.resultLayout.tvDesc.text="آپ نے امتحان پاس کر لیا ہے۔"
                        binding.resultLayout.tvChooseAnotherCourse.text="آپ کا LSBE کورس کا سفر"
                        setTextViewFont(binding.resultLayout.tvCongo,R.font.alvi_nastaleeq_regular,requireContext(),24)
                        setTextViewFont(binding.resultLayout.tvStatus,R.font.alvi_nastaleeq_regular,requireContext(),28)
                        setTextViewFont(binding.resultLayout.tvDesc,R.font.alvi_nastaleeq_regular,requireContext(),18)
                        setTextViewFont(binding.resultLayout.tvChooseAnotherCourse,R.font.alvi_nastaleeq_regular,requireContext(),26)
                    }
                }
                else if (it.isFailed){
                    binding.bottomLayoutPost.l_first.visibility=View.GONE
                    binding.bottomLayoutPost.l_second.visibility=View.VISIBLE
                    binding.bottomLayoutPost.tv_no_of_qs.text=it.totalQuestions.toString()
                    binding.bottomLayoutPost.tv_no_of_correct_answer.text=it.postCorrectAns.toString()
                    binding.bottomLayoutPost.tv_no_of_incorrect_answer.text=it.postIncorrectAns.toString()

                    binding.resultLayout.root.visibility=View.VISIBLE
                    binding.resultLayout.btnTestAgain.visibility=View.VISIBLE
                    binding.resultLayout.tvStatus.text=getString(R.string.label_test_failed)
                    binding.resultLayout.tvCongo.text = getString(R.string.label_ops)
                    binding.resultLayout.tvCongo.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                    binding.resultLayout.tvStatus.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                    binding.resultLayout.tvDesc.text = getString(R.string.label_desc_failed)
                    binding.resultLayout.tvDesc.visibility=View.GONE

                    if (isUrduMedium){
                        binding.resultLayout.tvCongo.text="دوبارہ کوشش کریں"
                        binding.resultLayout.tvStatus.text="ناکام ہو چکے ہیں"
                        binding.resultLayout.tvDesc.text="آپ امتحان میں ناکام ہو گئے ہیں"
                        binding.resultLayout.tvDesc.visibility=View.GONE
                        binding.resultLayout.tvChooseAnotherCourse.text="آپ کا LSBE کورس کا سفر"
                        setTextViewFont(binding.resultLayout.tvCongo,R.font.alvi_nastaleeq_regular,requireContext(),24)
                        setTextViewFont(binding.resultLayout.tvStatus,R.font.alvi_nastaleeq_regular,requireContext(),28)
                        setTextViewFont(binding.resultLayout.tvDesc,R.font.alvi_nastaleeq_regular,requireContext(),18)
                        setTextViewFont(binding.resultLayout.tvChooseAnotherCourse,R.font.alvi_nastaleeq_regular,requireContext(),26)
                    }

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

    fun getDescriptionText(description: String): CharSequence? {
        return  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(description,Html.FROM_HTML_MODE_COMPACT).trim()
        } else {
            HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT).trim()
        }
    }



    inner class Browser_home : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.progressbar.visibility = View.VISIBLE
            binding.progressbars.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
           binding.progressbar.visibility = View.GONE
           binding.progressbars.visibility = View.GONE

            if (mcqSubmittedAndShowResultAtBottom && isPostTest==true){
                binding.appbar.setExpanded(false, true)
                binding.scrollview.post(Runnable { binding.scrollview.fullScroll(ScrollView.FOCUS_DOWN) })
                mcqSubmittedAndShowResultAtBottom=false
                isPostTest=false
            }
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
//            val toast: Toast = Toast.makeText(context, R.string.test_already_taken, Toast.LENGTH_SHORT)
//            val toastLayout = toast.view as LinearLayout?
//            val toastTV = toastLayout!!.getChildAt(0) as TextView
//            val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_regular)
//            toastTV.typeface = typeface
//            toast.show()
           Toast.makeText(context, R.string.test_already_taken, Toast.LENGTH_SHORT).show()
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

    override fun onStart() {
        super.onStart()
        loadLocate(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        loadLocate(requireActivity())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        loadLocate(requireActivity())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadLocate(requireActivity())
    }
}