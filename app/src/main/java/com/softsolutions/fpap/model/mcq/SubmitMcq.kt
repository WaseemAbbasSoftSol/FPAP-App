package com.softsolutions.fpap.model.mcq

import com.google.gson.annotations.SerializedName

data class SubmitMcq(
    @SerializedName("MemberId")
    val memberId: Int,
    @SerializedName("TestId")
    val testId: Int,
    @SerializedName("QuestionId")
    val questionId: Int,
    @SerializedName("AnswerId")
    val answerId: Int,
    //Below this are useless
    @SerializedName("MarksPerQuestion")
    val marksPerQuestion: Int=0,
    @SerializedName("TotalQuestions")
    val totalQuestions: Int=0,
    @SerializedName("AssignmentId")
    val assignmentId: Int=0,
) {
}