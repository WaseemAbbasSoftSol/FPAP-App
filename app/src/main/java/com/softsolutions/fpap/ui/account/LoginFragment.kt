package com.softsolutions.fpap.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolutions.fpap.MainActivity
import com.softsolutions.fpap.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.base_toolbar.view.*

class LoginFragment: Fragment() {
    private lateinit var binding:FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.toolbar.tv_toolbar.text="Login"
       binding.tvForgotPass.setOnClickListener{
          findNavController().navigate(LoginFragmentDirections.actionLoginfragmentToRecoverPasswordFragment())
       }
        binding.tvRegisterNow.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginfragmentToSignupFragment())
        }
        binding.btnLogin.setOnClickListener{
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
        return binding.root
    }
}