package com.softsolutions.fpap.ui.main.dashboard.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentDashboardDetailBinding
import com.softsolutions.fpap.ui.common.isUrduMedium
import com.softsolutions.fpap.utils.setTextViewFont
import kotlinx.android.synthetic.main.layout_start_test_bottom.view.*


class DashboardDetailFragment:Fragment() {
    private lateinit var binding:FragmentDashboardDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardDetailBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        if (isUrduMedium) {
            binding.back.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_back_right))
//            setTextViewFont(binding.tvHeader,R.font.alvi_nastaleeq_regular,requireContext(),26)
//            setTextViewFont(binding.tvToolbar,R.font.alvi_nastaleeq_regular,requireContext(),26)
//            setTextViewFont(binding.tvDetail,R.font.alvi_nastaleeq_regular,requireContext(),20)
//            setTextViewFont(binding.tvDetail2,R.font.alvi_nastaleeq_regular,requireContext(),20)
//            setTextViewFont(binding.tvDetail3,R.font.alvi_nastaleeq_regular,requireContext(),20)
//            setTextViewFont(binding.bottomLayout.tv_start_test,R.font.alvi_nastaleeq_regular,requireContext(),22)
//            setTextViewFont(binding.bottomLayout.tv_step1,R.font.alvi_nastaleeq_regular,requireContext(),20)
//            setTextViewFont(binding.bottomLayout.tv_pre_test,R.font.alvi_nastaleeq_regular,requireContext(),22)
//            setTextViewFont(binding.bottomLayout.tv_question,R.font.alvi_nastaleeq_regular,requireContext(),20)
//            setTextViewFont(binding.bottomLayout.tv_no_of_question,R.font.alvi_nastaleeq_regular,requireContext(),22)
//            setTextViewFont(binding.bottomLayout.tv_correct_answer,R.font.alvi_nastaleeq_regular,requireContext(),20)
//            setTextViewFont(binding.bottomLayout.tv_no_of_corrent_answer,R.font.alvi_nastaleeq_regular,requireContext(),22)
//            setTextViewFont(binding.bottomLayout.tv_incorrect_answer,R.font.alvi_nastaleeq_regular,requireContext(),20)
//            setTextViewFont(binding.bottomLayout.tv_no_of_incorrent_answer,R.font.alvi_nastaleeq_regular,requireContext(),22)
//            setTextViewFont(binding.bottomLayout.btn_start_test,R.font.alvi_nastaleeq_regular,requireContext(),18)

        }
        binding.bottomLayout.btn_start_test.setOnClickListener {
            findNavController().navigate(DashboardDetailFragmentDirections.actionDashboardDetailToMcqsFragment())
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
}