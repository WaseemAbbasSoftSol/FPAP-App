package com.softsolutions.fpap.ui.main.certificate

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentCertificateBinding
import com.softsolutions.fpap.ui.common.isUrduMedium
import com.softsolutions.fpap.utils.DownloadHelper
import com.softsolutions.fpap.utils.getMimeType
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class CertificateFragment:Fragment() {
    private lateinit var binding:FragmentCertificateBinding
    private val mViewModel:CertificateViewModel by viewModel()
    private var pdfCertificate = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCertificateBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.tvToolbar.text=getString(R.string.label_certificate)
       // binding.clBottom.visibility= View.GONE
        binding.tvCongo.visibility=View.GONE
        binding.tvDesc.text = getString(R.string.label_pass_all_course)
        binding.btnDownload.setOnClickListener {
            checkPermissions(pdfCertificate)
        }
        if (isUrduMedium) {
            binding.back.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_right))
        }
//        val list= arrayListOf<CourseCertificate>()
//        list.add(CourseCertificate(0,"Human Rights", "Not Attempt","01-10-2022","","انسانی حقوق"))
//        list.add(CourseCertificate(0,"Gender", "Passed","01-10-2022","","صنف"))
//        list.add(CourseCertificate(0,"Communication", "Failed","01-10-2022","","مواصلات"))
//        list.add(CourseCertificate(0,"Decision making", "In Progress","01-10-2022","","فیصلہ سازی"))
//        list.add(CourseCertificate(0,"Culture Diversity and Values", "Not Attempt","01-10-2022","","ثقافتی تنوع اور اقدار"))
//        list.add(CourseCertificate(0,"Health and Hygiene", "Passed","01-10-2022","","صحت اور حفظان صحت"))
//        list.add(CourseCertificate(0,"Interpersonal Relationships", "Failed","01-10-2022","","باہمی تعلقات"))
//        list.add(CourseCertificate(0,"Protection against voilence", "In Progress","01-10-2022","","تشدد کے خلاف تحفظ"))
//        val layoutManager= LinearLayoutManager(requireContext())
//        val adapter= CertificateAdapter(requireContext(),list)
//        binding.rvCertificate.layoutManager=layoutManager
//        binding.rvCertificate.adapter=adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.certificate.observe(viewLifecycleOwner){
            if (it.certificateDetail.isNotEmpty()){
                binding.progressbar.visibility=View.GONE
                val layoutManager= LinearLayoutManager(requireContext())
               val adapter= CertificateAdapter(requireContext(),it.certificateDetail)
                binding.rvCertificate.layoutManager=layoutManager
                binding.rvCertificate.adapter=adapter
                if (it.isPassedAllCourse){
                    pdfCertificate=it.certificateLink
                    binding.btnDownload.visibility=View.VISIBLE
                    binding.tvCongo.visibility=View.VISIBLE
                    binding.tvDesc.text=resources.getString(R.string.label_desc1)
                }
            }
        }
    }

    private fun checkPermissions(url:String) {
        if (Build.VERSION.SDK_INT >= 23) {
            val permList = ArrayList<String>()
            if (PermissionChecker.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.INTERNET
                ) != PermissionChecker.PERMISSION_GRANTED) {
                permList.add(Manifest.permission.INTERNET)
            }
            if (PermissionChecker.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PermissionChecker.PERMISSION_GRANTED) {
                permList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (PermissionChecker.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PermissionChecker.PERMISSION_GRANTED) {
                permList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (permList.size > 0) {
                val perms = arrayOfNulls<String>(permList.size)
                for (x in perms.indices) {
                    perms[x] = permList[x]
                }
                requestPermissions(perms, 1)
            } else {
                Toast.makeText(requireContext(), "downloading", Toast.LENGTH_SHORT).show()
                DownloadHelper.downloadFile(
                    url,
                    getMimeType(url),
                    requireContext()
                )
                DownloadHelper.setDownloadTaskListener(object : DownloadHelper.DownloadingTask {
                    override  fun onDownloadFailed() {}
                    override fun onDownloadComplete() {}
                })
            }
        }
    }
}