package com.softsolutions.fpap

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.softsolutions.fpap.databinding.ActivityMainBinding
import com.softsolutions.fpap.ui.account.SignoutDialog
import com.softsolutions.fpap.ui.common.FragmentOnBackPressed
import com.softsolutions.fpap.ui.common.isUrduMedium
import com.softsolutions.fpap.ui.main.certificate.CertificateActivity
import com.softsolutions.fpap.ui.main.profile.ProfileActivity
import com.softsolutions.fpap.utils.loadLocate
import com.softsolutions.fpap.utils.setLocate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_main)
        visibilityNavElements(navController)
        binding.toolbar.tvToolbar.text = resources.getString(R.string.dashboard_toolbar)
        binding.toolbar.navHamMenu.setOnClickListener {
            binding.drawerlayout.openDrawer(GravityCompat.START)
        }
        val headerView = binding.navigationView.inflateHeaderView(R.layout.drawer_header_layout)
        val switchCompat = headerView.findViewById<SwitchCompat>(R.id.sth_med)
        val back = headerView.findViewById<ImageFilterView>(R.id.back)
        switchCompat.isChecked = isUrduMedium
        if (isUrduMedium) {
            back.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back_right))
        }

        switchCompat.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
       //  binding.drawerlayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
                setLocate("ur",this)
               finish()
                startActivity(Intent(this,MainActivity::class.java))
            }
            else {
                //binding.drawerlayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
                setLocate("en",this)
                finish()
                startActivity(Intent(this,MainActivity::class.java))

            }
        })

        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> ""
                R.id.certificate->{
                    //findNavController(R.id.nav_host_main).navigate(R.id.action_global_to_certificate_fragment)
                    startActivity(Intent(this, CertificateActivity::class.java))
                }
              //  R.id.profile->findNavController(R.id.nav_host_main).navigate(R.id.action_global_to_profile_fragment)
                R.id.profile->{
                    startActivity(Intent(this, ProfileActivity::class.java))
                }

                R.id.signout->{
                    val dialog= SignoutDialog()
                   // dialog.show(supportFragmentManager, "")
                }

            }
            binding.drawerlayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.back.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back))
            when (destination.id) {
                R.id.dashboard_fragment -> {
                 //   binding.mainAppbar.visibility = View.VISIBLE
                    // UiHelpers.setCustomMargins(binding.mainToolbar, 12, 0,0,0)
                    binding.toolbar.root.visibility= View.VISIBLE
                    binding.toolbar.navHamMenu.visibility=View.VISIBLE
                    binding.toolbar.back.visibility=View.GONE
                    //binding.toolbar.back.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_ham_menu))
                }
                R.id.dashboard_detail_fragment, R.id.mcqs_fragment,R.id.result_fragment -> {
                  //  binding.toolbar.back.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back))
                    binding.toolbar.root.visibility= View.GONE
                    binding.toolbar.navHamMenu.visibility=View.GONE
                }
                else -> {
                    hideNavigation()

                }
            }
        }

    }
    private fun hideNavigation() { //Hide both drawer and bottom navigation bar
        binding.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //Locked mode on swipe
       // binding.mainAppbar.visibility = View.GONE
    }

    override fun onBackPressed() {
        when(val currentFragment = nav_host_main.childFragmentManager.fragments[0]) {
            is FragmentOnBackPressed -> currentFragment.onBackPressed()
            else -> if(!navController.popBackStack()) finish()
        }
    }

}