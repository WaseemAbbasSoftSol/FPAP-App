package com.softsolutions.fpap.ui.main.certificate

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsolutions.fpap.databinding.FragmentCertificateBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CertificateActivity : AppCompatActivity() {
    private lateinit var binding:FragmentCertificateBinding
    private val mViewModel:CertificateViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentCertificateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val locale = Locale("en")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        binding.tvToolbar.text="Certificate"

        binding.back.setOnClickListener {
            onBackPressed()
        }

        mViewModel.certificate.observe(this){
            if (it.isNotEmpty()){
                val layoutManager= LinearLayoutManager(this)
                val adapter= CertificateAdapter(this,it)
                binding.rvCertificate.layoutManager=layoutManager
                binding.rvCertificate.adapter=adapter
            }
        }
    }

}