package com.softsolutions.fpap.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DashboardDetail(
    @SerializedName("IntroContent")
    val introContent: String,
    @SerializedName("IntroTitle")
    val introTitle: String,
    @SerializedName("CourseContent")
    val courseContent: String,
    @SerializedName("TitleCourseContent")
    val titleCourseContent: String,
    @SerializedName("Video")
    val video: String,
    @SerializedName("TestId")
    val testId: Int,
    @SerializedName("TotalQuestion")
    val totalQuestions: Int,
    @SerializedName("PreCorrectAns")
    val preCorrectAns: Int,
    @SerializedName("PerIncorrectAns")
    val preIncorrectAns: Int,
    @SerializedName("PostCorrectAns")
    val postCorrectAns: Int,
    @SerializedName("PostIncorrectAns")
    val postIncorrectAns: Int,
    @SerializedName("UnitID")
    val unitId: Int,
    @SerializedName("Ismedium")
    val isMedium: Boolean,
    @SerializedName("SubjectImage")
    val subjectImage: String,
    @SerializedName("MCQsLink")
    val mcqsLink: String,
    @SerializedName("IsSubmitPreTest")
    val isSumbmittedPreTest: Boolean,
    @SerializedName("IsSubmitPostTest")
    val isSubmittedPostTest: Boolean,
    @SerializedName("IsPassed")
    val isPassed:Boolean,
    @SerializedName("IsFailed")
    val isFailed:Boolean,
    @SerializedName("subjlist")
    val subjectList:List<SubjectList>,
    @SerializedName("CoursedetailLink")
    val courseContentLinkDetail:String
):Serializable
