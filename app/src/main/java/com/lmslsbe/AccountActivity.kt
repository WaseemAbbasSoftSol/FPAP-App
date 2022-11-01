package com.lmslsbe

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.lmslsbe.ui.common.FragmentOnBackPressed
import kotlinx.android.synthetic.main.activity_account.*
import java.util.*


class AccountActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locale = Locale("en")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        setContentView(R.layout.activity_account)
        navController = findNavController(R.id.nav_host_account)

    }

    override fun onBackPressed() {
        when(val currentFragment = nav_host_account.childFragmentManager.fragments[0]) {
            is FragmentOnBackPressed -> currentFragment.onBackPressed()
            else -> if(!navController.popBackStack()) finish()
        }
    }
}