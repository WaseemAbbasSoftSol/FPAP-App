package com.softsolutions.fpap

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsolutions.fpap.databinding.FragmentCertificateBinding
import com.softsolutions.fpap.ui.main.certificate.CertificateAdapter
import kotlinx.android.synthetic.main.base_toolbar.view.*
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

        binding.toolbarLayout.tv_toolbar.text="Certificate"
        val layoutManager= LinearLayoutManager(this)
        val adapter= CertificateAdapter(this)
        binding.rvCertificate.adapter=adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}