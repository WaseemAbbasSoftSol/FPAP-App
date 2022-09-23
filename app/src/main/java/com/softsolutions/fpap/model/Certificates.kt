package com.softsolutions.fpap.model

import com.google.gson.annotations.SerializedName

data class Certificates(
    @SerializedName("Certdetail")
    val certificateDetail: List<CertificateDetail>,
    @SerializedName("Certificatelink")
    val certificateLink: String,
    @SerializedName("IsPassedAllCourses")
    val isPassedAllCourse: Boolean,

    )
