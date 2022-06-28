package com.softsolutions.fpap.model.account

import com.google.gson.annotations.SerializedName

data class Register(
    @SerializedName("emailid")
    val email: String,
    @SerializedName("PhoneNumber")
    val phoneNumber: String,
    @SerializedName("QualificationId")
    val qualificationId: Int,
    @SerializedName("RegionId")
    val regionId: Int,
    @SerializedName("CityId")
    val cityId: Int,
    @SerializedName("Password")
    val password: String,
    @SerializedName("DOB")
    val dob: String,
    @SerializedName("GenderName")
    val gender: String,
)
