package com.softsolutions.fpap.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.softsolutions.fpap.MainActivity
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private val mViewModel:AccountViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.toolbar.tvToolbar.text="Login"
        binding.toolbar.back.visibility=View.GONE
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
//        binding.btnLogin.setOnClickListener {
//            if (validateFields()){
//                makeProgressOnButton(binding.btnLogin, R.string.plz_wait)
//                mViewModel.login(binding.edEmail.text.toString().trim(), binding.edPass.text.toString().trim())
//            }
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=mViewModel
        mViewModel.user.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                mViewModel.deleteUser()
                mViewModel.saveUser(it)
            }
        })
    }
    private fun validateFields():Boolean{
        return when {
            binding.edEmail.text.toString().trim().isEmpty() -> {
                binding.etEmail.error=getString(R.string.label_field_required)
                false
            }
            binding.edPass.text.toString().trim().isEmpty() -> {
                binding.etPass.error=getString(R.string.label_field_required)
                false
            }
            else -> true
        }
    }
}