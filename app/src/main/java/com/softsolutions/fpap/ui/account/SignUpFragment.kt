package com.softsolutions.fpap.ui.account

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolutions.fpap.databinding.FragmentSignupBinding
import kotlinx.android.synthetic.main.base_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val mViewModel:AccountViewModel by viewModel()

    private val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.toolbar.tvToolbar.text="New User Registration"
        binding.tvExistingUser.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignupFragmentToLoginFragment())
        }

        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = day
                updateLabel()
            }

        binding.tvDob.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()

        }
        return binding.root
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.tvDob.setText(dateFormat.format(myCalendar.time))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=mViewModel
    }
}