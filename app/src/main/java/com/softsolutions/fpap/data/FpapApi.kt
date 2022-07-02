package com.softsolutions.fpap.data

import com.softsolutions.fpap.model.BaseResponse
import com.softsolutions.fpap.model.DashboardDetail
import com.softsolutions.fpap.model.account.ForgotPassword
import com.softsolutions.fpap.model.account.Login
import com.softsolutions.fpap.model.account.Register
import com.softsolutions.fpap.model.account.User
import com.softsolutions.fpap.model.common.BaseCommonList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface FpapApi {

    @POST("register")
    @Headers("Content-Type:application/json")
    suspend fun register(
        @Body params:Register
    ):Response<BaseResponse<User>>

    @POST("signin")
    @Headers("Content-Type:application/json")
    suspend fun login(
        @Body params:Login
    ):Response<BaseResponse<User>>

    @POST("forgetpassword")
    @Headers("Content-Type:application/json")
    suspend fun forgotPassword(
        @Body params:ForgotPassword
    ):Response<BaseResponse<User>>

    @POST("getQualificationlist")
    suspend fun getQualificationList():Response<BaseResponse<List<BaseCommonList>>>

    @POST("getRegionlist")
    suspend fun getRegionList():Response<BaseResponse<List<BaseCommonList>>>


    @POST("getcitylist")
    suspend fun getCitiesList():Response<BaseResponse<List<BaseCommonList>>>

    @POST("dashboard")
    suspend fun getDashboardData(
        @Query("MemberID")memberId:Int
    ):Response<BaseResponse<User>>

    @POST("getCoursedetail")
    suspend fun getDashboardDetail(
        @Query("ClassId")classId:Int,
        @Query("SubjectId")subjectId:Int,
        @Query("medium")medium:Boolean,
    ):Response<BaseResponse<DashboardDetail>>

}