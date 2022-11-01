package com.lmslsbe.model

import com.google.gson.annotations.SerializedName

data class CountedUsers(
    @SerializedName("TotalUser")
    val registeredUsers:Int,
    @SerializedName("TotalPartial")
    val partialUsers:Int,
    @SerializedName("TotalSingal")
    val singalUsers:Int,
    @SerializedName("TotalCompleted")
    val completedUsers:Int,
)
