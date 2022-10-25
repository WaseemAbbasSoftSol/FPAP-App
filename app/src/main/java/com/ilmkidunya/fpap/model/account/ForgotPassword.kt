package com.ilmkidunya.fpap.model.account

import com.google.gson.annotations.SerializedName

data class ForgotPassword(
    @SerializedName("email")
    val email:String
)
