package com.softsolutions.fpap.ui.main.dashboard

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentDashboardBinding
import com.softsolutions.fpap.model.SubjectList
import com.softsolutions.fpap.ui.common.*
import com.softsolutions.fpap.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardFragment : Fragment(), FragmentOnBackPressed {
    private lateinit var binding: FragmentDashboardBinding
    private val mViewModel: DashboardViewModel by viewModel()
    private var back = 0
    private var selectedLanguage=""
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadLocate(requireActivity())
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        requireActivity().exitFullScreenMode()
        binding.subjectClickListener = OnDashboardSubjectClickListener()
        getNumberAndMakePhoneCall()

//        if (isNewStudentRegistering){
//            val dialog = SelectMediumDialog()
//            dialog.show(requireActivity().supportFragmentManager, "")
//            dialog.setDialogPositiveClick(object :
//                SelectMediumDialog.OnDialogPositiveButtonClickListener {
//                override fun onyesButtonClik(isUrduMediumSelected: Boolean) {
//                    isNewStudentRegistering=false
//                    if (isUrduMediumSelected){
//                        isUrduMedium=true
//                        mViewModel.updateLanguage(true)
//                        selectedLanguage = "ur"
//                    }
//                    else {
//                        mViewModel.updateLanguage(false)
//                        selectedLanguage = "en"
//                        isUrduMedium=true
//                    }
//                }
//            })
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=mViewModel
        mViewModel.updateLanguageMsg.observe(viewLifecycleOwner){
            if (it == LANGUAGE_UPDATED_SUCCESSFULLY){
                if (selectedLanguage=="ur"){
                    setLocate("ur", requireActivity())
                    requireActivity().finish()
                    requireActivity().overridePendingTransition(0, 0);
                    startActivity(requireActivity().intent);
                    requireActivity().overridePendingTransition(0, 0);
                }else{
                    setLocate("en", requireActivity())
                    requireActivity().finish()
                    requireActivity().overridePendingTransition(0, 0);
                    startActivity(requireActivity().intent);
                    requireActivity().overridePendingTransition(0, 0);
                   // mViewModel.getDashboardData()
                }

            }
        }
    }

    inner class OnDashboardSubjectClickListener : OnListItemClickListener<SubjectList> {
        override fun onItemClick(item: SubjectList, pos: Int) {
            val subjeName= if (!item.subName().isNullOrEmpty())item.subName() else ""
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardToDashboardDetailFragment(
                    item.id,subjeName
                )
            )
        }
    }

    override fun onBackPressed() {
        if (back==0){

            val toast: Toast = Toast.makeText(context, R.string.press_back_again, Toast.LENGTH_SHORT)
            val toastLayout = toast.view as LinearLayout?
            val toastTV = toastLayout!!.getChildAt(0) as TextView
            val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_regular)
            toastTV.typeface = typeface
            toast.show()

            back++
            Handler(Looper.getMainLooper()).postDelayed({
                back = 0
            }, 2000)
            return
        }
        requireActivity().finish()
    }

    private fun getNumberAndMakePhoneCall() {
        binding.tvLhrNo.setOnClickListener {
            val no = binding.tvLhrNo.text.toString().trim()
            makePhoneCall(no)
        }
        binding.tvIsbNo.setOnClickListener {
            val no = binding.tvIsbNo.text.toString().trim()
            makePhoneCall(no)
        }
        binding.tvKhiNo.setOnClickListener {
            val no = binding.tvKhiNo.text.toString().trim()
            makePhoneCall(no)
        }
        binding.tvQuettaNo.setOnClickListener {
            val no = binding.tvQuettaNo.text.toString().trim()
            makePhoneCall(no)
        }
        binding.tvPeshawarNo.setOnClickListener {
            val no = binding.tvPeshawarNo.text.toString().trim()
            makePhoneCall(no)
        }
        binding.tvGbNo.setOnClickListener {
            val no = binding.tvGbNo.text.toString().trim()
            makePhoneCall(no)
        }
        binding.tvNationalNo.setOnClickListener {
            val no = binding.tvNationalNo.text.toString().trim()
            makePhoneCall(no)
        }
        binding.tvPunjabNo.setOnClickListener {
            val no = binding.tvPunjabNo.text.toString().trim()
            makePhoneCall(no)
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),
                1000
            )
        } else {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }
    }
}