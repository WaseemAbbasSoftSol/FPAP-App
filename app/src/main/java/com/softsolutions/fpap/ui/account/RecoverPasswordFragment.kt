package com.softsolutions.fpap.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentRecoverPasswordBinding
import com.softsolutions.fpap.model.account.ForgotPassword
import kotlinx.android.synthetic.main.base_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecoverPasswordFragment: Fragment() {
    private lateinit var binding:FragmentRecoverPasswordBinding
    private val mViewModel:AccountViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRecoverPasswordBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.toolbar.tvToolbar.text="Password Recovery"

        binding.btnSend.setOnClickListener {
            if (binding.edEmail.text.toString().isEmpty()){
                binding.etEmail.error=getString(R.string.label_enter_email)
                return@setOnClickListener
            }
          val email=ForgotPassword(binding.edEmail.text.toString().trim())
            mViewModel.recoverForgotPassword(email)
        }
        return binding.root
    }

}