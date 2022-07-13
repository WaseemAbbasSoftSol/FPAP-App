package com.softsolutions.fpap.model.mcq

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Mcq(
    @SerializedName("ID")
    val questionId: Int,
    @SerializedName("TestID")
    val testId: Int,
    @SerializedName("UnitID")
    val unitId: Int,
    @SerializedName("QuestionText")
    val questionText: String,
    @SerializedName("QuestionImage")
    val questionImage: String,
    @SerializedName("Answers")
    val answer: List<McqsOption>,
    var isAnySelected:Boolean=false,
    var isOp:Int=0,
    var wrongSelectedPosition:Int=-1,
    var isWrongSelected:Boolean=false
):Serializable
