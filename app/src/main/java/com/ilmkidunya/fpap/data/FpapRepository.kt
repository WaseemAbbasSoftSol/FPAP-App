package com.ilmkidunya.fpap.data

import com.ilmkidunya.fpap.model.UpdateProfile
import com.ilmkidunya.fpap.model.account.ForgotPassword
import com.ilmkidunya.fpap.model.account.Login
import com.ilmkidunya.fpap.model.account.Register
import com.ilmkidunya.fpap.model.mcq.SubmitMcq
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
 suspend fun getCountedUsers()=fpapApi.getCountedUsers()
 suspend fun updateLanguage(memberId: Int, isUrduMedium:Boolean)=fpapApi.updateLanguage(memberId,isUrduMedium)
 suspend fun updateTransgenderSubject(memberId: Int, subjectId: Int)=fpapApi.updateTransgenderSubject(memberId, subjectId)
 suspend fun getDashboardDetail(memberId: Int,classId:Int, subjectId:Int, medium:Boolean)=fpapApi.getDashboardDetail(memberId,classId,subjectId, medium)
 suspend fun update(update:UpdateProfile)=fpapApi.update(update)
 suspend fun updateProfileImage(memberId: RequestBody, image: MultipartBody.Part?) = fpapApi.updateProfileImage(memberId, image)
 suspend fun getCourseCertificate(memberId: Int)=fpapApi.getCourseCertificate(memberId)
 suspend fun getMcqsList(unitId:Int, testId:Int)=fpapApi.getMcqsList(unitId, testId)
 suspend fun submitMcq(mcqsList:List<SubmitMcq>)=fpapApi.submitMcqsTest(mcqsList)
}