package com.softsolutions.fpap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.ui.common.isEnglishMedium

private const val SPLASH_TIME_OUT=1000L
class SplashActivity : AppCompatActivity() {
    private lateinit var prefRepository: PrefRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        prefRepository= PrefRepository(application)

        if (prefRepository.getMedium()!=null){
            isEnglishMedium = prefRepository.getMedium()=="English Medium"
        }else isEnglishMedium = true

        Handler(Looper.getMainLooper()).postDelayed({
            goToAccountActivity()
        }, SPLASH_TIME_OUT)
    }

    private fun goToAccountActivity() {
        startActivity(Intent(this, AccountActivity::class.java))
        finish()
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}