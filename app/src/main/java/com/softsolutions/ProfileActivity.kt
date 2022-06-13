package com.softsolutions

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.softsolutions.fpap.CustomActivity
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.ActivityCustomBinding
import com.softsolutions.fpap.databinding.FragmentCertificateBinding
import com.softsolutions.fpap.databinding.FragmentProfileBinding
import com.softsolutions.fpap.ui.account.SignoutDialog
import com.softsolutions.fpap.ui.isUrduMedium
import com.softsolutions.fpap.utils.loadLocate
import com.softsolutions.fpap.utils.setLocate
import com.softsolutions.fpap.utils.setTextViewFont
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