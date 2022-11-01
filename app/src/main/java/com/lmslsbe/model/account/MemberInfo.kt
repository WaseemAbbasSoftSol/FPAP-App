package com.lmslsbe.model.account

import com.google.gson.annotations.SerializedName
import com.lmslsbe.utils.splitDateAndTime

data class MemberInfo (
    @SerializedName("ID")
    val Id:Int, //MemberId
    @SerializedName("Name")
    val name : String,
    @SerializedName("Image")
    val image : String,
    @SerializedName("ClassId")
    val classId : Int,
    @SerializedName("MenuUrl")
    val menuUrl : String,
    @SerializedName("MenuUrl_v2")
    val menuUrl_v2 : String,
    @SerializedName("BoardId")
    val boardId : Int,
    @SerializedName("StudyGroup")
    val studyGroupId : Int,
    @SerializedName("ClassName")
    val className : String,
    @SerializedName("BoardName")
    val boardName : String,
    @SerializedName("StudyGroupName")
    val studyGroupName : String,
    @SerializedName("Medium")
    val medium : Int,
    @SerializedName("LastName")
    val lastName : String,
    @SerializedName("Links")
    val links : String,
    @SerializedName("MemberType")
    val memberType : Int,
    @SerializedName("Profile")
    val profile : String,
    @SerializedName("IsForLive")
    val isForLive : Boolean,
    @SerializedName("IsMobileVerify")
    val isMobileVerify : Boolean,
    @SerializedName("IsAdminTeacher")
    val isAdminTeacher : Boolean,
    @SerializedName("IsPaid")
    val isPaid : Boolean,
    @SerializedName("IsDevice")
    val isDevice : String,
    @SerializedName("ClassUrl")
    val classUrl : String,
    @SerializedName("ExpiredDate")
    val expiredDate : String,
    @SerializedName("Section")
    val section : Int,
    @SerializedName("Session")
    val session : Int,
    @SerializedName("SectionUrl")
    val sectionUrl : String,
    @SerializedName("SessionUrl")
    val sessionUrl : String,
    @SerializedName("ProvinceId")
    val provinceId : Int,
    @SerializedName("LivePage")
    val livePage : String,
    @SerializedName("DOB")
    val dob:String,
    @SerializedName("CityId")
    val cityId:Int,
    @SerializedName("QualificationId")
    val qualificationId:Int,
    @SerializedName("RegionId")
    val regionId:Int,
    @SerializedName("GenderName")
    val gender:String,
    @SerializedName("EmailId")
    val email:String,
    @SerializedName("IsLanguage")
    val isUrduMedium:Boolean,
    @SerializedName("CountryName")
    val countryCodeName:String,
    @SerializedName("TrangendersubjectId")
    val transgenderSubjectId:Int

){
    val dateOfBirth:String
    get() = splitDateAndTime(dob)
}