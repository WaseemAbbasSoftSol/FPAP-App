package com.softsolutions.fpap.model

data class Dashboard(
    val image:String,
    val title:String
){
    override fun toString(): String = title
}
