package com.softsolutions.fpap.ui.account

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentSignupBinding
import com.softsolutions.fpap.model.account.Register
import com.softsolutions.fpap.model.common.BaseCommonList
import kotlinx.android.synthetic.main.base_toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val mViewModel:AccountViewModel by viewModel()

    private val myCalendar: Calendar = Calendar.getInstance()

    private val countryCode="92"
    private var qualificationId=0

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

        binding.spQualification.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position)
                if (item is BaseCommonList) run {
                    val board: BaseCommonList = item
                    Toast.makeText(requireContext(),"${board.value}, ${board.text}", Toast.LENGTH_SHORT).show()
                }
            }

        binding.btnRegister.setOnClickListener {
            if (validateFields()){
                val number=binding.edNumber.text.toString()
                val mobileNumber="$countryCode$number"
                val register=Register(binding.edEmail.text.toString(),mobileNumber,0,0,0, binding.edPass.text.toString()
                ,binding.tvDob.text.toString(),"")
                mViewModel.register(register)
            }
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
        mViewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it!=null){
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
            binding.edNumber.text.toString().trim().isEmpty() -> {
                binding.etNumber.error=getString(R.string.label_field_required)
                false
            }
            binding.edRegion.text.toString().trim().isEmpty() -> {
                binding.etRegion.error=getString(R.string.label_field_required)
                false
            }
            binding.tvDob.text.toString().trim().isEmpty() -> {
                binding.etDob.error=getString(R.string.label_field_required)
                false
            }
            binding.edEmail2.text.toString().trim().isEmpty() -> {
                binding.etEmail2.error=getString(R.string.label_field_required)
                false
            }
            binding.edPass.text.toString().trim().isEmpty() -> {
                binding.etPass.error=getString(R.string.label_field_required)
                false
            }
            binding.edConfirmPass.text.toString().trim() != binding.edPass.text.toString().trim() -> {
                binding.etConfirmPass.error=getString(R.string.label_pass_not_matched)
                false
            }
            else -> true
        }
    }
}