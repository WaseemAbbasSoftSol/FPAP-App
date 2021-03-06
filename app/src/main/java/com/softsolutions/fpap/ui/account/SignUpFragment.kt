package com.softsolutions.fpap.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolutions.fpap.databinding.FragmentSignupBinding
import kotlinx.android.synthetic.main.base_toolbar.view.*


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.toolbar.tv_toolbar.text="New User Registration"
        binding.tvExistingUser.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignupFragmentToLoginFragment())
        }
        return binding.root
    }
}