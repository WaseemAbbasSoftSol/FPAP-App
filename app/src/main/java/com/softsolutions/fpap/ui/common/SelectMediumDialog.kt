package com.softsolutions.fpap.ui.common

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.softsolutions.fpap.databinding.DialogSubmitTestBinding
import com.softsolutions.fpap.databinding.DialogUpdateLanguageBinding

class SelectMediumDialog : DialogFragment() {
    private lateinit var binding: DialogUpdateLanguageBinding
    private var listener: OnDialogPositiveButtonClickListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DialogUpdateLanguageBinding.inflate(inflater, container, false)
        binding.lifecycleOwner=this
        binding.btnEng!!.setOnClickListener {
            listener!!.onyesButtonClik(false)
            dialog!!.dismiss()
        }
        binding.btnUrdu.setOnClickListener {
            listener!!.onyesButtonClik(true)
            dialog!!.dismiss()
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = (resources.displayMetrics.widthPixels * 0.75).toInt()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val back = ColorDrawable(Color.TRANSPARENT)
            val inset = InsetDrawable(back, 35)
            dialog.window!!.setBackgroundDrawable(inset)
            dialog.setCancelable(false)

            //  dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    interface OnDialogPositiveButtonClickListener{
        fun onyesButtonClik(isUrduMediumSelected:Boolean)
    }

    fun setDialogPositiveClick(listener: OnDialogPositiveButtonClickListener) {
        this.listener = listener
    }
}