package com.lmslsbe.ui.account

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.lmslsbe.MainActivity
import com.lmslsbe.R
import com.lmslsbe.databinding.FragmentLoginBinding

import com.lmslsbe.model.account.Login
import com.lmslsbe.ui.common.isNewStudentRegistering
import com.lmslsbe.ui.common.isUrduMedium
import com.lmslsbe.utils.MEMBER_SIGNIN_SUCCESSFULLY
import com.lmslsbe.utils.hideProgressOnButton
import com.lmslsbe.utils.makeProgressOnButton
import com.lmslsbe.utils.setLocate
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment: Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val mViewModel: AccountViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        isNewStudentRegistering =false
        binding.toolbar.tvToolbar.text="Login"
       binding.tvForgotPass.setOnClickListener{
          findNavController().navigate(LoginFragmentDirections.actionLoginfragmentToRecoverPasswordFragment())
       }
        binding.tvRegisterNow.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginfragmentToSignupFragment())
        }
        binding.btnLogin.setOnClickListener {
            if (validateFields()){
                makeProgressOnButton(binding.btnLogin, R.string.plz_wait)
                val login=
                    Login(binding.edEmail.text.toString().trim(), binding.edPass.text.toString().trim())
                mViewModel.login(login)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=mViewModel
        mViewModel.user.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                if (it.isRegistered){
                    if (null != it.memberInfo!!.countryCodeName) mViewModel.saveCountryCode(it.memberInfo!!.countryCodeName)
                    isUrduMedium =it.memberInfo.isUrduMedium
                    if (it.memberInfo.isUrduMedium)  setLocate("ur", requireActivity())
                    else  setLocate("en", requireActivity())
                    mViewModel.deleteUser()
                    mViewModel.saveUser(it)
                }
            }
        })

        mViewModel.message.observe(viewLifecycleOwner) {
            if (it == MEMBER_SIGNIN_SUCCESSFULLY) {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
        mViewModel.errorMessage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.isNotEmpty()){
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                hideProgressOnButton(binding.btnLogin, "LOGIN")
            }
        })
      //  hideValidation(binding.edEmail, binding.etEmail)
       // hideValidation(binding.edPass, binding.etPass)
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

  private  fun hideValidation(ed:EditText, tx:TextInputLayout){
        ed.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isNotEmpty()) {
                    tx.error = ""
                } else {
                    tx.error = getString(R.string.label_field_required)
                }
            }
        })
    }
}