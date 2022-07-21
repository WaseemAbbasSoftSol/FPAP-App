package com.softsolutions.fpap.model

import com.google.gson.annotations.SerializedName

data class UpdateProfile(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("emailid")
    val emailId: String,
    @SerializedName("PhoneNumber")
    val phoneNumber: String,
    @SerializedName("QualificationId")
    val qualificationId: Int,
    @SerializedName("RegionId")
    val regionId: Int,
    @SerializedName("CityId")
    val cityId: Int,
    @SerializedName("DOB")
    val dob:String,
    @SerializedName("GenderName")
    val genderName:String,
    @SerializedName("CountryName")
    val countryCodeName:String
) {
}