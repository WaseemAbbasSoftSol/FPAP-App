package com.softsolutions.fpap.ui.account

import androidx.lifecycle.ViewModel
import com.softsolutions.fpap.model.Dashboard

class AccountViewModel(

):ViewModel(){
    val qualificationList= arrayListOf<Dashboard>(
        Dashboard("", "Matric"),
        Dashboard("", "Intermidiate"),
        Dashboard("", "Bachelors"),
        Dashboard("", "Mphil"),
    )
}