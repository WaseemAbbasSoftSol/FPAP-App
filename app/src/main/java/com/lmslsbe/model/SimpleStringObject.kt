package com.lmslsbe.model

import com.google.gson.annotations.SerializedName

data class SimpleStringObject(
    @SerializedName("Message")
    val message:String
)