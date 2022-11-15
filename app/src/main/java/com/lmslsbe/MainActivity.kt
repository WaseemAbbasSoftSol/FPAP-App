package com.lmslsbe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.lmslsbe.data.PrefRepository
import com.lmslsbe.databinding.ActivityMainBinding

import com.lmslsbe.ui.account.SignoutDialog
import com.lmslsbe.ui.common.FragmentOnBackPressed
import com.lmslsbe.ui.common.isProfileImageChanged
import com.lmslsbe.ui.common.isUrduMedium
import com.lmslsbe.ui.main.profile.ProfileActivity
import com.lmslsbe.utils.loadLocate
import com.lmslsbe.utils.setLocate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var prefRepository: PrefRepository
    private lateinit var tvName: TextView
    private lateinit var tvClass: TextView
    private lateinit var memberImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefRepository = PrefRepository(application)
        navController = findNavController(R.id.nav_host_main)
        visibilityNavElements(navController)
        binding.toolbar.tvToolbar.text = resources.getString(R.string.dashboard_toolbar)
        binding.toolbar.navHamMenu.setOnClickListener {
            binding.drawerlayout.openDrawer(GravityCompat.START)
        }
        val headerView = binding.navigationView.inflateHeaderView(R.layout.drawer_header_layout)

        tvName = headerView.findViewById(R.id.tv_nav_header_name)
        tvClass = headerView.findViewById(R.id.tv_nav_header_class)
        memberImage = headerView.findViewById(R.id.profile_image)
        setNavHeaderViews()
        val switchCompat = headerView.findViewById<SwitchCompat>(R.id.sth_med)
        val back = headerView.findViewById<ImageFilterView>(R.id.back)
        back.setOnClickListener {
           hideNavigation()
        }
        switchCompat.isChecked = isUrduMedium
        if (isUrduMedium) {
            back.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_back_right_white))
            binding.navigationView.background=resources.getDrawable(R.drawable.bg_drawer_urdu)
        }

        switchCompat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                isUrduMedium =true
                setLocate("ur", this)
                finish()
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            } else {
                isUrduMedium =false
                setLocate("en", this)
                finish()
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }

        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> hideNavigation()
               // R.id.certificate-> startActivity(Intent(this, CertificateActivity::class.java))
                R.id.certificate -> findNavController(R.id.nav_host_main).navigate(R.id.action_dashboard_certificate_fragment)
                R.id.profile -> startActivity(Intent(this, ProfileActivity::class.java))
                R.id.signout -> {
                    val dialog = SignoutDialog()
                    dialog.show(supportFragmentManager, "")
                }
            }
            binding.drawerlayout.closeDrawer(GravityCompat.START)
            true
        }
        isProfileImageChanged.observe(this) {
            if (it) {
                setNavHeaderViews()
            }
        }
    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.back.setImageDrawable(ContextCompat.getDrawable(this,
                R.drawable.ic_back
            ))
            when (destination.id) {
                R.id.dashboard_fragment -> {
                    binding.toolbar.root.visibility= View.VISIBLE
                    binding.toolbar.toplogo.visibility=View.VISIBLE
                    binding.toolbar.navHamMenu.visibility=View.VISIBLE
                    binding.toolbar.back.visibility=View.GONE
                    binding.toolbar.profile.visibility=View.VISIBLE
                }
                R.id.dashboard_detail_fragment, R.id.result_fragment, R.id.fragment_certificate -> {
                    binding.toolbar.root.visibility= View.GONE
                    binding.toolbar.navHamMenu.visibility=View.GONE
                }
                else -> {
                    hideNavigation()

                }
            }
        }

    }
    private fun hideNavigation() = binding.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    override fun onBackPressed() {
        when (val currentFragment = nav_host_main.childFragmentManager.fragments[0]) {
            is FragmentOnBackPressed -> currentFragment.onBackPressed()
            else -> if (!navController.popBackStack()) finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setNavHeaderViews() {
        tvName.text = prefRepository.getUser()!!.memberInfo.name
        tvClass.text = prefRepository.getUser()!!.memberInfo.className
        val img = prefRepository.getUser()!!.memberInfo.image
        Glide.with(this).load(img).into(memberImage)
        Glide.with(this).load(img).into(binding.toolbar.profile)

    }
}