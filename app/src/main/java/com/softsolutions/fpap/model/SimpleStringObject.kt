package com.softsolutions.fpap.model

import com.google.gson.annotations.SerializedName

data class SimpleStringObject(
    @SerializedName("Message")
    val message:String
)