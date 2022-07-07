package com.softsolutions.fpap.ui.main.dashboard.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentDashboardDetailBinding
import com.softsolutions.fpap.ui.common.isUrduMedium
import com.softsolutions.fpap.utils.setTextViewFont
import kotlinx.android.synthetic.main.layout_start_test_bottom.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardDetailFragment:Fragment() {
    private lateinit var binding:FragmentDashboardDetailBinding
    private val mViewModel:DashboardDetailViewModel by viewModel()
    private var imageLink="https://ikddata.ilmkidunya.com/images/subjectimages/"
    private var testId=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            val args=DashboardDetailFragmentArgs.fromBundle(it!!)
            mViewModel.subjectId=args.subjectId
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardDetailBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        if (isUrduMedium) {
            binding.back.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_back_right))
        }
        binding.bottomLayout.btn_start_test.setOnClickListener {
            findNavController().navigate(DashboardDetailFragmentDirections.actionDashboardDetailToMcqsFragment(testId))
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
                    isShow = true
                } else if (isShow) {
                    binding.tvHeader.visibility=View.VISIBLE
                    binding.tvToolbar.visibility=View.GONE
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
                testId=it.testId
                binding.tvToolbar.text=it.introTitle
                binding.tvHeader.text=it.introTitle
                imageLink += it.subjectImage
                Glide.with(requireContext()).load(imageLink).into(binding.guideDetailImage)
            }
        }
    }
}