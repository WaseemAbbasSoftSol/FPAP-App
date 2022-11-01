package com.lmslsbe.model

import com.google.gson.annotations.SerializedName

data class CertificateDetail(
    @SerializedName("SubjectId")
    val subjectId: Int,
    @SerializedName("SubjectName")
    val subjectName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("Date")
    val date: String,
    @SerializedName("DownloadFile")
    val file: String,
    @SerializedName("SubjectUrduName")
    val urduName:String
)

