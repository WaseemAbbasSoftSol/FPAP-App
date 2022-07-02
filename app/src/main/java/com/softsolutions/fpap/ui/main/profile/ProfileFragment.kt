package com.softsolutions.fpap.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.softsolutions.fpap.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment:Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private val mViewModel:ProfileViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.llEdit.setOnClickListener {
            Toast.makeText(requireContext(),"Clicked", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=mViewModel
        mViewModel.qualificationList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                for ((i,value) in it.withIndex()){
                    if (mViewModel.qualificationId.toString() == value.value){
                        binding.spQualification.setText(value.text)
                        break
                    }
                }
            }
        })

        mViewModel.regionList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                for ((i,value) in it.withIndex()){
                    if (mViewModel.regionId.toString() == value.value){
                        binding.edRegion.setText(value.text)
                        break
                    }
                }
            }
        })

        mViewModel.citiesList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                for ((i,value) in it.withIndex()){
                    if (mViewModel.cityId.toString() == value.value){
                        binding.spCity.setText(value.text)
                        break
                    }
                }
            }
        })
    }

}