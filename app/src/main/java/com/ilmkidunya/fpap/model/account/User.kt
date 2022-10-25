package com.ilmkidunya.fpap.model.account

import com.google.gson.annotations.SerializedName
import com.ilmkidunya.fpap.model.SubjectList

data class User(
    @SerializedName("Phonenumber")
    val phoneNumber:String,
    @SerializedName("MemberID")
    val memberId:Int,
    @SerializedName("IsRegistered")
    val isRegistered:Boolean,
    @SerializedName("IsVerified")
    val isVerified: Boolean,
    @SerializedName("IsBlocked")
    val isBlocked: Boolean,
    @SerializedName("MemberInfo")
    val memberInfo : MemberInfo,
    @SerializedName("SubjectList")
    val subjectList : List<SubjectList>,
){
    fun removeCountryCodeFromNumber():String{
        return  phoneNumber.substring(2)
    }
}
