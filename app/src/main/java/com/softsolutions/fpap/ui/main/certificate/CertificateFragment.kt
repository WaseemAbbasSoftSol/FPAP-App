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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.certificate.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                val layoutManager= LinearLayoutManager(requireContext())
                val adapter= CertificateAdapter(requireContext(),it)
                binding.rvCertificate.layoutManager=layoutManager
                binding.rvCertificate.adapter=adapter
            }
        }
    }
}