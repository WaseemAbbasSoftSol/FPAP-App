package com.softsolutions.fpap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.ui.common.isUrduMedium

private const val SPLASH_TIME_OUT=1000L
class SplashActivity : AppCompatActivity() {
    private lateinit var prefRepository: PrefRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        prefRepository= PrefRepository(application)

        isUrduMedium = if (prefRepository.getMedium()!=null){
            prefRepository.getMedium()=="ur"
        }else false

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