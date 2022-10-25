package com.ilmkidunya.fpap.model.mcq

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class McqsOption(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("AnswerText")
    val answerText: String,
    @SerializedName("AnswerImage")
    val answerImage: String,
    @SerializedName("Correct")
    val isCorrect: Boolean,
):Serializable
