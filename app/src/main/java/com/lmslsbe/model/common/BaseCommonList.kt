package com.lmslsbe.model.common

import com.google.gson.annotations.SerializedName

class BaseCommonList(
    @SerializedName("Text")
    val text:String,
    @SerializedName("Value")
    val value:String
){
    override fun toString(): String = text
}