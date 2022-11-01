package com.lmslsbe.model.account

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("Email")
    val email:String,
    @SerializedName("Password")
    val password:String
)
