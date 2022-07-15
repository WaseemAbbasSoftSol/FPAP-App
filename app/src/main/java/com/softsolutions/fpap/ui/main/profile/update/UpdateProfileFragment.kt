package com.softsolutions.fpap.ui.main.profile.update

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.softsolutions.fpap.MainActivity
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentUpdateProfileBinding
import com.softsolutions.fpap.model.UpdateProfile
import com.softsolutions.fpap.model.common.BaseCommonList
import com.softsolutions.fpap.ui.common.isProfileImageChanged
import com.softsolutions.fpap.ui.main.profile.ProfileFragment
import com.softsolutions.fpap.ui.main.profile.ProfileViewModel
import com.softsolutions.fpap.utils.*
import com.zhihu.matisse.Matisse
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class UpdateProfileFragment : Fragment() {
    private lateinit var binding: FragmentUpdateProfileBinding
    private val mViewModel: ProfileViewModel by viewModel()
    private val countryCode="92"
    private val myCalendar: Calendar = Calendar.getInstance()
    private var dob=""

    private var mImage: File? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.frameLayout.setOnClickListener {
            FilePicker(this).launch(1)
        }
        binding.btnUpdate.setOnClickListener {
            makeProgressOnButton(binding.btnUpdate, R.string.plz_wait)
            val phoneNumber=countryCode+binding.edNumber.text.toString()
            val update= UpdateProfile(mViewModel.memberId,binding.edName.text.toString().trim(), binding.edEmail.text.toString().trim(),
            phoneNumber,mViewModel.qualificationId,mViewModel.regionId,mViewModel.cityId,dob,mViewModel.genderId)
            mViewModel.update(update)
        }

        binding.spQualification.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position)
                if (item is BaseCommonList) run {
                    val board: BaseCommonList = item
                    mViewModel.qualificationId=board.value.toInt()
                }
            }

        binding.edRegion.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position)
                if (item is BaseCommonList) run {
                    val region: BaseCommonList = item
                    mViewModel.regionId=region.value.toInt()
                }
            }

        binding.spCity.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position)
                if (item is BaseCommonList) run {
                    val city: BaseCommonList = item
                    mViewModel.cityId=city.value.toInt()
                }
            }

        binding.spGender.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent.getItemAtPosition(position)
                if (item is BaseCommonList) run {
                    val gender: BaseCommonList = item
                    mViewModel.genderId=gender.value
                }
            }

        val date =
            DatePickerDialog.OnDateSetListener { view, year, month, day ->
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = mViewModel
        mViewModel.user.observe(viewLifecycleOwner){
            if (it!=null){
                val d=it.memberInfo.dob
                dob = d.substring(0, d.indexOf('T'))
            }
        }
        mViewModel.updateUser.observe(viewLifecycleOwner) {
            if (it != null) {
                ProfileFragment.profileImage.value=it.memberInfo.image
                mViewModel.deleteUser()
                mViewModel.saveUser(it)
            }
        }
        mViewModel.message.observe(viewLifecycleOwner){
            if (it== MEMBER_UPDATED){
                Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(),MainActivity::class.java))
                requireActivity().finish()
            }
        }
        mViewModel.errorMessage.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                Toast.makeText(requireContext(), it.toString(),Toast.LENGTH_SHORT).show()
                hideProgressOnButton(binding.btnUpdate,"update")
            }
        }
        mViewModel.imageUpdateMessage.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                isProfileImageChanged.value=true
                binding.imageProgrss.visibility=View.GONE
                Toast.makeText(requireContext(), it.toString(),Toast.LENGTH_SHORT).show()
            }
        }
        mViewModel.qualificationList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                for ((i, value) in it.withIndex()) {
                    if (mViewModel.qualificationId.toString() == value.value) {
                        binding.spQualification.setText(value.text)
                        break
                    }
                }
            }
        }
        mViewModel.citiesList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                for ((i, value) in it.withIndex()) {
                    if (mViewModel.cityId.toString() == value.value) {
                        binding.spCity.setText(value.text)
                        break
                    }
                }
            }
        }
        mViewModel.regionList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                for ((i, value) in it.withIndex()) {
                    if (mViewModel.regionId.toString() == value.value) {
                        binding.edRegion.setText(value.text)
                        break
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                REQUEST_IMAGE -> {
                    var selectedUris: List<Uri>?=null
                    selectedUris = Matisse.obtainResult(data)
                    if (selectedUris.isNullOrEmpty()) return

                    Glide.with(this).load(selectedUris!![0]).into(binding.circleImageView)
                    mImage = createSelectedFileCopy(selectedUris!![0], requireContext())

                    CoroutineScope(Dispatchers.IO).launch {
                        val compressedImageFile = Compressor.compress(requireContext(), mImage!!)
                        {
                            default(format = Bitmap.CompressFormat.JPEG)
                        }
                        mViewModel.updateProfileImage(compressedImageFile)
                    }
                    binding.imageProgrss.visibility=View.VISIBLE
                }
            }
        }

    }
    private fun updateLabel() {
        val myFormat = "dd MMM, yyyy"
        val serverFormat="yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        val dateFormatForServer = SimpleDateFormat(serverFormat, Locale.US)
        dob=dateFormatForServer.format(myCalendar.time)
        binding.tvDob.setText(dateFormat.format(myCalendar.time))
    }
}