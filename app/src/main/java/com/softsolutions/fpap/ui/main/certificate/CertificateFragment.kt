package com.softsolutions.fpap.ui.main.certificate

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsolutions.fpap.databinding.FragmentCertificateBinding
import kotlinx.android.synthetic.main.base_toolbar.view.*

class CertificateFragment:Fragment() {
    private lateinit var binding:FragmentCertificateBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCertificateBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.toolbarLayout.tv_toolbar.text="Certificate"
      val layoutManager=LinearLayoutManager(requireContext())
        val adapter=CertificateAdapter(requireContext())
        binding.rvCertificate.adapter=adapter
        return binding.root
    }
}