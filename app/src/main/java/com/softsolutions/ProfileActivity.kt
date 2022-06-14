package com.softsolutions

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softsolutions.fpap.CustomActivity
import com.softsolutions.fpap.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.base_toolbar.view.*
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

        binding.toolbarLayout.tv_toolbar.text="Profile"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, CustomActivity::class.java))
        finish()
    }
}