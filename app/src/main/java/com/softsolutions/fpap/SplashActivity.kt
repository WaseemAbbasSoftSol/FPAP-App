package com.softsolutions.fpap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

private const val SPLASH_TIME_OUT=1000L
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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