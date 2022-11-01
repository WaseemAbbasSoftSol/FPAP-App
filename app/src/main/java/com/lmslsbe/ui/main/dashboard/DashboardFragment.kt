package com.lmslsbe.ui.main.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.lmslsbe.R
import com.lmslsbe.databinding.FragmentDashboardBinding
import com.lmslsbe.model.SubjectList
import com.lmslsbe.ui.common.DialogUpdateTransgenderSubject
import com.lmslsbe.ui.common.FragmentOnBackPressed
import com.lmslsbe.ui.common.OnListItemClickListener
import com.lmslsbe.ui.common.isTransgenderSubjectSelectedAndUpdateDashboardData
import com.lmslsbe.utils.LANGUAGE_UPDATED_SUCCESSFULLY
import com.lmslsbe.utils.exitFullScreenMode
import com.lmslsbe.utils.loadLocate
import com.lmslsbe.utils.setLocate
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardFragment : Fragment(), FragmentOnBackPressed {
    private lateinit var binding: FragmentDashboardBinding
    private val mViewModel: DashboardViewModel by viewModel()
    private var back = 0
    private var transgenderSubjectId=0
    private var selectedLanguage=""
    var permissions = arrayOf(
        Manifest.permission.CALL_PHONE
    )
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
      //  binding.subjectClickListener=OnDashboardSubjectClickListener()
        binding.subjectClickListener=OnDashboardSubjectClickListener()
        getNumberAndMakePhoneCall()

        if (isTransgenderSubjectSelectedAndUpdateDashboardData){
            mViewModel.getDashboardData()
            isTransgenderSubjectSelectedAndUpdateDashboardData =false
        }

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
        mViewModel.user.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                transgenderSubjectId=it.memberInfo.transgenderSubjectId
            }
        })
        mViewModel.updateTrasnsgenderSubjectMsg.observe(viewLifecycleOwner, Observer {
            if (it=="Transgender subject updated sucessfully"){
                findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardToDashboardDetailFragment(
                        subjectId, courseName
                    )
                )
                isTransgenderSubjectSelectedAndUpdateDashboardData =true
            }
        })
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
        @SuppressLint("SuspiciousIndentation")
        override fun onItemClick(item: SubjectList, pos: Int) {
        if (mViewModel.gender=="Trans Gender                                      " && transgenderSubjectId==0){
            if (item.id==765 || item.id==762){
            val dialog = DialogUpdateTransgenderSubject()
            dialog.show(requireActivity().supportFragmentManager, "")
            dialog.setTransgenderUpdateButtonClick(object :
                DialogUpdateTransgenderSubject.UpdateTransgenderSubjectButtonClickListener {
                override fun onButtonClick(subject: String) {
                    subjectId =if (subject=="male") 762 else 765
                  mViewModel.updateTransgenderSubject(subjectId)
                }
            })
                return
            }
            else if(mViewModel.gender=="Trans Gender                                      "){
                subjectId =item.id
            }
        }
        else subjectId = item.id
            val subjeName= if (!item.subName().isNullOrEmpty())item.subName() else ""
            courseName =subjeName
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardToDashboardDetailFragment(
                    subjectId,subjeName
                )
            )
        }
    }

    override fun onBackPressed() {
        if (back==0){

//            val toast: Toast = Toast.makeText(context, R.string.press_back_again, Toast.LENGTH_SHORT)
//            val toastLayout = toast.view as LinearLayout?
//            val toastTV = toastLayout!!.getChildAt(0) as TextView
//            val typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_regular)
//            toastTV.typeface = typeface
//            toast.show()
            Toast.makeText(context, R.string.press_back_again, Toast.LENGTH_SHORT).show()
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

        Permissions.check(
            requireContext(),
            permissions,
            null,
            null,
            object : PermissionHandler() {
                @RequiresApi(Build.VERSION_CODES.KITKAT)
                override fun onGranted() {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                    startActivity(intent)
                }

                override fun onDenied(
                    context: Context?,
                    deniedPermissions: java.util.ArrayList<String>?
                ) {
                    Toast.makeText(requireContext(),"Permission denied", Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object{
        var courseName = ""
        var subjectId=0
    }
}