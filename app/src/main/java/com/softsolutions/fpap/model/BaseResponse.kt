package com.softsolutions.fpap.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("Response_Status")
    val responseStatus:Int,
    @SerializedName("Response_Message")
    val responseMessage:String,
    @SerializedName("Error_Message")
    val errorMessage:String,
    @SerializedName("data")
    val data : T
)
