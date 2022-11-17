package com.lmslsbe.model.account

import com.google.gson.annotations.SerializedName
import com.lmslsbe.model.SubjectList

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
        var no=""
        try {
            no=  phoneNumber.substring(2)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return  no
    }
}
