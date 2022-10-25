package com.ilmkidunya.fpap.model

import com.google.gson.annotations.SerializedName
import com.ilmkidunya.fpap.ui.common.isUrduMedium

data class SubjectList (
    @SerializedName("SubjectTypes")
    val subjectType: String,
    @SerializedName("LeftMenu")
    val leftMenu: String,
    @SerializedName("ItemType")
    val itemType: Int,
    @SerializedName("ClassUrl")
    val classUrl: String,
    @SerializedName("InstituteUrl")
    val instituteUrl: String,
    @SerializedName("IsPaid")
    val isPaid: Boolean,
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("NameUrdu")
    val urduName:String,
    @SerializedName("Url")
    val url: String,
    @SerializedName("Image")
    val image: String,
    @SerializedName("Image1")
    val image1: String,
    @SerializedName("Status")
    val status: Boolean,
    @SerializedName("Dated")
    val date: String,
    @SerializedName("Total")
    val total: Int,
    @SerializedName("Total_2")
    val total2: Int,
    @SerializedName("Url2")
    val url2: String,
    @SerializedName("City")
    val city: String,
    @SerializedName("Id_Second")
    val idSec: Int,
    @SerializedName("PageNo")
    val pageNo: Int,
    @SerializedName("Token")
    val token: String,
    @SerializedName("PreviousClass")
    val previousClass: String,
    @SerializedName("FormId")
    val formId: Int,
    @SerializedName("Attempted")
    val attempted: Boolean,
    @SerializedName("IsActive")
    val isActive: Boolean,
    @SerializedName("IsOpen")
    val isOpen: Boolean,
    @SerializedName("IsRedirectToLivePage")
    val isRedirectToLivePage: Boolean,
    @SerializedName("HasBoard")
    val hasBoard: Boolean,
    @SerializedName("HasGroup")
    val hasGroup: Boolean,
    @SerializedName("IsAppShowing")
    val isAppShowing: Boolean,
    @SerializedName("IsPassed")
    val isPassed:Boolean
){
    fun subName():String{
        return if (isUrduMedium)
            urduName
        else name
    }
}