package com.softsolutions.fpap.data

import com.softsolutions.fpap.model.account.ForgotPassword
import com.softsolutions.fpap.model.account.Register

class FpapRepository(private val fpapApi: FpapApi) {
 suspend fun register(register: Register)=fpapApi.register(register)
 suspend fun login(email:String, password:String)=fpapApi.login(email, password)
 suspend fun forgotPassword(email: ForgotPassword)=fpapApi.forgotPassword(email)
 suspend fun getQualificationList()=fpapApi.getQualificationList()
 suspend fun getRegionList()=fpapApi.getRegionList()
 suspend fun getCitiesList()=fpapApi.getCitiesList()
 suspend fun getDashboardData(memberId:Int)=fpapApi.getDashboardData(memberId)
}