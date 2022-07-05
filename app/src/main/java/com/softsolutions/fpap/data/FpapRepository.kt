package com.softsolutions.fpap.data

import com.softsolutions.fpap.model.UpdateProfile
import com.softsolutions.fpap.model.account.ForgotPassword
import com.softsolutions.fpap.model.account.Login
import com.softsolutions.fpap.model.account.Register
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FpapRepository(private val fpapApi: FpapApi) {
 suspend fun register(register: Register)=fpapApi.register(register)
 suspend fun login(login:Login)=fpapApi.login(login)
 suspend fun forgotPassword(email: ForgotPassword)=fpapApi.forgotPassword(email)
 suspend fun getQualificationList()=fpapApi.getQualificationList()
 suspend fun getRegionList()=fpapApi.getRegionList()
 suspend fun getCitiesList()=fpapApi.getCitiesList()
 suspend fun getDashboardData(memberId:Int)=fpapApi.getDashboardData(memberId)
 suspend fun getDashboardDetail(classId:Int, subjectId:Int, medium:Boolean)=fpapApi.getDashboardDetail(classId,subjectId, medium)
 suspend fun update(update:UpdateProfile)=fpapApi.update(update)
 suspend fun updateProfileImage(memberId: RequestBody, image: MultipartBody.Part?) = fpapApi.updateProfileImage(memberId, image)
 suspend fun getCourseCertificate(memberId: Int)=fpapApi.getCourseCertificate(memberId)
}