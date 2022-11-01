package com.lmslsbe.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.lmslsbe.R
import com.lmslsbe.databinding.FragmentProfileBinding
import com.lmslsbe.ui.main.profile.update.UpdateProfileFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment:Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val mViewModel: ProfileViewModel by viewModel()
    private var countryNameCode="pk"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        if (mViewModel.getCountryCodeNameFromPref()!=null){
            countryNameCode=mViewModel.getCountryCodeNameFromPref()!!
        }
        binding.llEdit.setOnClickListener {
           goToUpdateProfile()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=mViewModel
        profileImage.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                Glide.with(this).load(it).into(binding.circleImageView)
            }
        }
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

    private fun goToUpdateProfile(){
        val fragment: Fragment = UpdateProfileFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.profile_frame, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    companion object{
        var profileImage=MutableLiveData<String>()
    }
}