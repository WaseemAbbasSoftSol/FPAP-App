package com.ilmkidunya.fpap.ui.account

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
import com.ilmkidunya.fpap.R
import com.ilmkidunya.fpap.databinding.FragmentRecoverPasswordBinding
import com.ilmkidunya.fpap.model.account.ForgotPassword
import com.ilmkidunya.fpap.utils.PASSWORD_SEND_TO_EMAIL
import com.ilmkidunya.fpap.utils.hideProgressOnButton
import com.ilmkidunya.fpap.utils.makeProgressOnButton
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
            makeProgressOnButton(binding.btnSend,R.string.plz_wait)
          val email=ForgotPassword(binding.edEmail.text.toString().trim())
            mViewModel.recoverForgotPassword(email)
        }
        hideValidation(binding.edEmail, binding.etEmail)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            if (it== PASSWORD_SEND_TO_EMAIL){
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }else{
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                hideProgressOnButton(binding.btnSend, "send email")
            }
        })
    }

    private  fun hideValidation(ed: EditText, tx: TextInputLayout){
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