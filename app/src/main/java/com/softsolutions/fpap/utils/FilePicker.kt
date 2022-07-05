package com.softsolutions.fpap.utils

import android.Manifest
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.softsolutions.fpap.R
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy

class FilePicker(private val fragment: Fragment) {

    private var maxSelection = 0
    var permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    fun launch(maxSelection: Int) {

        this.maxSelection = maxSelection

        if (Build.VERSION.SDK_INT >= 23) {
            if (fragment.activity?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                checkStoragePermission()
            }else{
                openGallery()
            }

        }else{
            openGallery()
        }
    }

    private fun openGallery() {
        Matisse.from(fragment)
            .choose(MimeType.ofAll())
            .countable(false)
            .maxSelectable(maxSelection)
            .capture(false)
            .captureStrategy(
                CaptureStrategy(true, "com.campus.pk.fileprovider", "campus")
            )
            .gridExpectedSize(fragment.resources.getDimensionPixelSize(R.dimen.grid_expected_size))
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .thumbnailScale(0.85f)
            .imageEngine(GlideEngine())
            .showSingleMediaType(true)
            .forResult(REQUEST_IMAGE)
    }

    private fun checkStoragePermission(){
        Permissions.check(
           fragment.requireContext(),
            permissions,
            null,
            null,
            object : PermissionHandler() {
                @RequiresApi(Build.VERSION_CODES.KITKAT)
                override fun onGranted() {
                 openGallery()
                }

                override fun onDenied(
                    context: Context?,
                    deniedPermissions: java.util.ArrayList<String>?
                ) {
                  Toast.makeText(fragment.requireContext(),"Permission denied", Toast.LENGTH_SHORT).show()
                }
            })

    }

}