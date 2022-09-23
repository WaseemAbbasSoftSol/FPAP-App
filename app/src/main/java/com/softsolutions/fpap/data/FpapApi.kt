package com.softsolutions.fpap.data

import com.softsolutions.fpap.model.*
import com.softsolutions.fpap.model.account.ForgotPassword
import com.softsolutions.fpap.model.account.Login
import com.softsolutions.fpap.model.account.Register
import com.softsolutions.fpap.model.account.User
import com.softsolutions.fpap.model.common.BaseCommonList
import com.softsolutions.fpap.model.mcq.Mcq
import com.softsolutions.fpap.model.mcq.SubmitMcq
import com.softsolutions.fpap.model.mcq.SubmittedMcq
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

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

    @POST("countuserdata")
    suspend fun getCountedUsers(
    ):Response<BaseResponse<CountedUsers>>

    @POST("updatelanguage")
    suspend fun updateLanguage(
        @Query("MemberId") memberId: Int,
        @Query("IsUrdumedium") isUrduMedium:Boolean
    ):Response<BaseResponse<Dashboard>>

    @POST("getCoursedetail")
    suspend fun getDashboardDetail(
        @Query("MemberId")memberId: Int,
        @Query("ClassId")classId:Int,
        @Query("SubjectId")subjectId:Int,
        @Query("medium")medium:Boolean,
    ):Response<BaseResponse<DashboardDetail>>


    @POST("update")
    @Headers("Content-Type:application/json")
    suspend fun update(
        @Body params:UpdateProfile
    ):Response<BaseResponse<User>>

    @Multipart
    @POST("updateProfileImage")
    suspend fun updateProfileImage(
        @Part("memberid") memberId: RequestBody,
        @Part ProfileImage: MultipartBody.Part?
    ): Response<BaseResponse<User>>

    @POST("getCourseCertificate")
    suspend fun getCourseCertificate(
       @Query("MemberID") memberId: Int
    ): Response<BaseResponse<Certificates>>


    @POST("fpapMCQsTest")
    suspend fun getMcqsList(
        @Query("UnitId") unitId: Int,
        @Query("TestId") testId: Int
    ): Response<BaseResponse<List<Mcq>>>

    @POST("MCqsSubmit")
    suspend fun submitMcqsTest(
        @Body params:List<SubmitMcq>
    ): Response<BaseResponse<SubmittedMcq>>
}