package com.lmslsbe.ui.main.profile

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lmslsbe.MainActivity
import com.lmslsbe.R
import com.lmslsbe.databinding.ActivityProfileBinding
import java.util.*


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val locale = Locale("en")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        binding.tvToolbar.text="Profile"

        val fragment = ProfileFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.profile_frame, fragment)
        transaction.commit()

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
       startActivity(Intent(this, MainActivity::class.java))
        finish()
}
}