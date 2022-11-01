package com.lmslsbe.model.mcq

import com.google.gson.annotations.SerializedName

data class SubmittedMcq(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("MemberId")
    val memberId: Int,
    @SerializedName("ObtainMarks")
    val obtainMarks: String,
    @SerializedName("TotalMarks")
    val totalMarks: String,
    @SerializedName("Dated")
    val date: String,
    @SerializedName("TestId")
    val testId: Int,
    @SerializedName("IsPassed")
    val isPassed: Boolean,
    @SerializedName("IsOfline")
    val isOffLine: Boolean,
    @SerializedName("IsActive")
    val isActive: Boolean,
    @SerializedName("MemberName")
    val memberName: String,
    @SerializedName("MemberImage")
    val memberImage: String,
    @SerializedName("DateStr")
    val dateStr: String,
    @SerializedName("Profile")
    val profile: String,
    @SerializedName("AlreadyExist")
    val alreadyExist: Boolean
)
