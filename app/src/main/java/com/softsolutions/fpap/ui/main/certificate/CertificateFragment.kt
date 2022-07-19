package com.softsolutions.fpap.ui.main.certificate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsolutions.fpap.MainActivity
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentCertificateBinding
import com.softsolutions.fpap.model.CourseCertificate
import com.softsolutions.fpap.ui.common.isUrduMedium
import org.koin.androidx.viewmodel.ext.android.viewModel

class CertificateFragment:Fragment() {
    private lateinit var binding:FragmentCertificateBinding
    private val mViewModel:CertificateViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCertificateBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.tvToolbar.text=getString(R.string.label_certificate)
        binding.clBottom.visibility= View.GONE
        if (isUrduMedium) {
            binding.back.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_right))
        }
        val list= arrayListOf<CourseCertificate>()
        list.add(CourseCertificate(0,"Human Rights", "Not Attempt","01-10-2022","","انسانی حقوق"))
        list.add(CourseCertificate(0,"Gender", "Passed","01-10-2022","","صنف"))
        list.add(CourseCertificate(0,"Communication", "Failed","01-10-2022","","مواصلات"))
        list.add(CourseCertificate(0,"Decision making", "In Progress","01-10-2022","","فیصلہ سازی"))
        list.add(CourseCertificate(0,"Culture Diversity and Values", "Not Attempt","01-10-2022","","ثقافتی تنوع اور اقدار"))
        list.add(CourseCertificate(0,"Health and Hygiene", "Passed","01-10-2022","","صحت اور حفظان صحت"))
        list.add(CourseCertificate(0,"Interpersonal Relationships", "Failed","01-10-2022","","باہمی تعلقات"))
        list.add(CourseCertificate(0,"Protection against voilence", "In Progress","01-10-2022","","تشدد کے خلاف تحفظ"))
        val layoutManager= LinearLayoutManager(requireContext())
        val adapter= CertificateAdapter(requireContext(),list)
        binding.rvCertificate.layoutManager=layoutManager
        binding.rvCertificate.adapter=adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.certificate.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
//                val layoutManager= LinearLayoutManager(requireContext())
//               val adapter= CertificateAdapter(requireContext(),it)
//                binding.rvCertificate.layoutManager=layoutManager
//                binding.rvCertificate.adapter=adapter
            }
        }
    }
}