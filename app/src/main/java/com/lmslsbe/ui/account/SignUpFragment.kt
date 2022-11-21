package com.lmslsbe.ui.account

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.lmslsbe.MainActivity
import com.lmslsbe.R
import com.lmslsbe.databinding.FragmentSignupBinding
import com.lmslsbe.model.account.Register
import com.lmslsbe.model.common.BaseCommonList
import com.lmslsbe.ui.common.isNewStudentRegistering
import com.lmslsbe.utils.MEMBER_SIGNUP_SUCCESSFULLY
import com.lmslsbe.utils.hideProgressOnButton
import com.lmslsbe.utils.makeProgressOnButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val mViewModel: AccountViewModel by viewModel()

    private val myCalendar: Calendar = Calendar.getInstance()

    private var countryCode="92"
    private var countryNameCode="pk"
    private var qualificationId=0
    private var regionId=0
    private var cityId=0
    private var gender=""
    private var dob=""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        isNewStudentRegistering =true
        binding.toolbar.back.visibility=View.GONE
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
                    qualificationId=board.value.toInt()
                }
            }

        binding.spRegion.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position)
                if (item is BaseCommonList) run {
                    val region: BaseCommonList = item
                    regionId=region.value.toInt()
                }
            }

        binding.spCity.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position)
                if (item is BaseCommonList) run {
                    val city: BaseCommonList = item
                    cityId=city.value.toInt()
                }
            }

        binding.spGender.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position)
                if (item is BaseCommonList) run {
                    val gende: BaseCommonList = item
                    gender=gende.value
                }
            }

        binding.btnRegister.setOnClickListener {
            if (validateFields()){
                makeProgressOnButton(binding.btnRegister, R.string.plz_wait)
                val number=binding.edNumber.text.toString()
                val mobileNumber="$countryCode$number"
                val register= Register(binding.edName.text.toString().trim(),binding.edEmail.text.toString(),mobileNumber,qualificationId,regionId,cityId,
                    binding.edPass.text.toString()
                ,dob,gender, countryNameCode)
                mViewModel.register(register)
            }

        }

        binding.ccp.setOnCountryChangeListener { selectedCountry ->
            countryCode = selectedCountry.phoneCode
            countryNameCode=selectedCountry.iso
            mViewModel.saveCountryCode(countryNameCode)
        }

        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.edEmail2.text = s
            }
        })
        return binding.root
    }

    private fun updateLabel() {
//        val myFormat = "MM/dd/yy"
//        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
//        binding.tvDob.setText(dateFormat.format(myCalendar.time))

        val myFormat = "dd MMM, yyyy"
        val serverFormat="yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        val dateFormatForServer = SimpleDateFormat(serverFormat, Locale.US)
        dob=dateFormatForServer.format(myCalendar.time)
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
        mViewModel.message.observe(viewLifecycleOwner) {
            if (it == MEMBER_SIGNUP_SUCCESSFULLY) {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
        mViewModel.errorMessage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.isNotEmpty()){
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                hideProgressOnButton(binding.btnRegister, "Register")
            }
        })
        mViewModel.qualificationList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.isNotEmpty()){
                //binding.spQualification.setText(it[0].text)
               // qualificationId=it[0].value.toInt()
            }
        })
        mViewModel.citiesList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.isNotEmpty()){
              //  binding.spCity.setText(it[0].text)
               // cityId=it[0].value.toInt()
            }
        })
        mViewModel.regionList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it.isNotEmpty()){
            //    binding.spRegion.setText(it[0].text)
              //  regionId=it[0].value.toInt()
            }
        })

//        hideValidation(binding.edName,binding.etName)
//        hideValidation(binding.edEmail,binding.etEmail)
//        hideValidation(binding.edNumber,binding.etNumber)
//        hideValidation(binding.tvDob,binding.etDob)
//        hideValidation(binding.edEmail2,binding.etEmail2)
//        hideValidation(binding.edPass,binding.etPass)
//        hideValidation(binding.edConfirmPass,binding.etConfirmPass)
//        hideValidation(binding.spQualification,binding.etQualification)
//        hideValidation(binding.spRegion,binding.etRegion)
//        hideValidation(binding.spCity,binding.etCity)
//        hideValidation(binding.spGender,binding.etGender)
    }

    private fun validateFields():Boolean{
        return when {
            binding.edName.text.toString().trim().isEmpty() -> {
                binding.etName.error=getString(R.string.label_field_required)
                false
            }
            binding.edEmail.text.toString().trim().isEmpty() -> {
                binding.etEmail.error=getString(R.string.label_field_required)
                false
            }
            !binding.edEmail.text.isValidEmail()->{
                binding.etEmail.error = getString(R.string.label_valid_email)
                return false
            }
            binding.edNumber.text.toString().trim().isEmpty() -> {
                binding.etNumber.error=getString(R.string.label_field_required)
                false
            }
            binding.edNumber.text.toString().trim().length<10 -> {
                binding.etNumber.error=getString(R.string.label_incomplete_no)
                false
            }
            qualificationId==0->{
                binding.etQualification.error = getString(R.string.label_field_required)
                false
            }
            regionId==0->{
                binding.etRegion.error = getString(R.string.label_field_required)
                false
            }
            cityId==0->{
                binding.etCity.error = getString(R.string.label_field_required)
                false
            }
            binding.tvDob.text.toString().trim().isEmpty() -> {
                binding.etDob.error=getString(R.string.label_field_required)
                false
            }
            gender==""->{
                binding.etGender.error = getString(R.string.label_field_required)
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
            binding.edPass.text.toString().trim().length<6 -> {
                binding.etPass.error = getString(R.string.lbl_min_pass)
                false
            }
            binding.edConfirmPass.text.toString().trim() != binding.edPass.text.toString().trim() -> {
                binding.etConfirmPass.error=getString(R.string.label_pass_not_matched)
                false
            }
            else -> true
        }
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

    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}