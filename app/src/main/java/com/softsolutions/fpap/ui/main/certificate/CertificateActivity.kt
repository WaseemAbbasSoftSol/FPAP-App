package com.softsolutions.fpap.ui.main.certificate

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsolutions.fpap.databinding.FragmentCertificateBinding
import java.util.*

class CertificateActivity : AppCompatActivity() {
    private lateinit var binding:FragmentCertificateBinding
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
        val layoutManager= LinearLayoutManager(this)
        val adapter= CertificateAdapter(this)
        binding.rvCertificate.adapter=adapter
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

}