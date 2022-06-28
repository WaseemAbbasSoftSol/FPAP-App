package com.softsolutions.fpap.model

import com.google.gson.annotations.SerializedName

data class SubjectList (
    @SerializedName("Name")
    val name:String,
    @SerializedName("Url")
    val url:String?=null,
    @SerializedName("Image1")
    val image:String,
    @SerializedName("IsPaid")
    val isPaid:Boolean
)