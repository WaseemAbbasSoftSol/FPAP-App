package com.softsolutions.fpap.ui.main.profile

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softsolutions.fpap.databinding.FragmentProfileBinding
import java.util.*


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding:FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val locale = Locale("en")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        binding.tvToolbar.text="Profile"
        binding.back.setOnClickListener {
            onBackPressed()
        }
    }


}