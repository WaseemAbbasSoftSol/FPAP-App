package com.softsolutions.fpap.ui.main.dashboard

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.softsolutions.fpap.R
import com.softsolutions.fpap.databinding.FragmentDashboardBinding
import com.softsolutions.fpap.model.Dashboard
import com.softsolutions.fpap.model.SubjectList
import com.softsolutions.fpap.ui.common.FragmentOnBackPressed
import com.softsolutions.fpap.ui.common.OnListItemClickListener
import com.softsolutions.fpap.ui.common.isUrduMedium
import org.koin.androidx.viewmodel.ext.android.viewModel


class DashboardFragment:Fragment(),FragmentOnBackPressed,OnListItemClickListener<SubjectList> {
    private lateinit var binding:FragmentDashboardBinding
    private val mViewModel:DashboardViewModel by viewModel()
    private var back=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardBinding.inflate(inflater,container,false)
        binding.lifecycleOwner=this
        binding.rvDashboard.showShimmerAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel=mViewModel
        mViewModel.user.observe(viewLifecycleOwner){
            if (it.subjectList.isNotEmpty()){
                val adapter=TempAdapter(requireContext(), it.subjectList,this)
                val layoutManager=GridLayoutManager(requireContext(), 3)
                binding.rvDashboard.layoutManager=layoutManager
                binding.rvDashboard.adapter=adapter
            }
        }
    }
    override fun onItemClick(item: SubjectList, pos: Int) {
       findNavController().navigate(DashboardFragmentDirections.actionDashboardToDashboardDetailFragment(item.id))
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
                back=0
            }, 2000)
            return
        }
        requireActivity().finish()
    }

}