package com.lmslsbe.ui.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmslsbe.data.FpapRepository
import com.lmslsbe.data.PrefRepository
import com.lmslsbe.model.RequestState
import com.lmslsbe.model.UpdateProfile
import com.lmslsbe.model.account.User
import com.lmslsbe.model.common.BaseCommonList
import com.lmslsbe.utils.APP_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ProfileViewModel(
    private val repository: FpapRepository,
    private val prefRepository: PrefRepository
):ViewModel() {

    private val _state = MutableLiveData<RequestState>()
    val state: LiveData<RequestState> = _state

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _imageUpdateMessage = MutableLiveData<String>()
    val imageUpdateMessage: LiveData<String> = _imageUpdateMessage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _updateUser = MutableLiveData<User>()
    val updateUser: LiveData<User> = _updateUser

    private val _qualifiationList = MutableLiveData<List<BaseCommonList>>()
    val qualificationList: LiveData<List<BaseCommonList>> = _qualifiationList

    private val _regionList = MutableLiveData<List<BaseCommonList>>()
    val regionList: LiveData<List<BaseCommonList>> = _regionList

    private val _citiesList = MutableLiveData<List<BaseCommonList>>()
    val citiesList: LiveData<List<BaseCommonList>> = _citiesList

    var memberId=0
    var qualificationId=0
    var regionId=0
    var cityId=0
    var genderId=""
    var countryCode="92"

    init {
        memberId=prefRepository.getUser()!!.memberId
        _qualifiationList.value = emptyList()
        _regionList.value = emptyList()
        _citiesList.value = emptyList()
        _user.value=prefRepository.getUser()!!
        qualificationId=prefRepository.getUser()!!.memberInfo.qualificationId
        regionId=prefRepository.getUser()!!.memberInfo.regionId
        cityId=prefRepository.getUser()!!.memberInfo.cityId
        genderId=prefRepository.getUser()!!.memberInfo.gender
        getQualificationLists()
        getRegionLists()
        getCitiesLists()
    }

    private fun getQualificationLists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getQualificationList()
                if (response.isSuccessful) {
                    response.body().let {
                        _qualifiationList.postValue(it!!.data)
                    }
                } else {
                    response.errorBody().let {
                        Log.d(APP_TAG, it!!.string())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    private fun getRegionLists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getRegionList()
                if (response.isSuccessful) {
                    response.body().let {
                        _regionList.postValue(it!!.data)
                    }
                } else {
                    response.errorBody().let {
                        Log.d(APP_TAG, it!!.string())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    private fun getCitiesLists() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCitiesList()
                if (response.isSuccessful) {
                    response.body().let {
                        _citiesList.postValue(it!!.data)
                    }
                } else {
                    response.errorBody().let {
                        Log.d(APP_TAG, it!!.string())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    fun update(updateProfile: UpdateProfile) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                val response = repository.update(updateProfile)
                if (response.isSuccessful) {
                    response.body().let {
                        _updateUser.postValue(it!!.data)
                        _message.postValue(it.responseMessage)
                        _errorMessage.postValue(it.errorMessage)
                    }
                } else {
                    response.errorBody().let {
                        Log.d(APP_TAG, it!!.string())
                        _message.postValue("An error has occurred")
                    }
                }
                _state.postValue(RequestState.DONE)
            } catch (e: Exception) {
                _state.postValue(RequestState.ERROR)
                e.printStackTrace()
            } catch (t: Throwable) {
                _state.postValue(RequestState.ERROR)
                t.printStackTrace()
            }
        }
    }

    fun updateProfileImage(file: File) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                var fileName: MultipartBody.Part? = null
                var mm: RequestBody? = null
                if (file != null) {
                    val rBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    fileName = MultipartBody.Part.createFormData("ProfileImage", file.name, rBody)
                }
                mm = memberId.toString()
                    .toRequestBody("multipart/form-data".toMediaTypeOrNull())

                val response = repository.updateProfileImage(mm!!, fileName!!)
                if (response.isSuccessful) {
                    response.body().let {
                        _updateUser.postValue(it!!.data!!)
                        _imageUpdateMessage.postValue(it.responseMessage)
                    }
                } else {
                    response.errorBody().let {
                        Log.d("TAG", it.toString())
                    }
                }
                _state.postValue(RequestState.DONE)
            } catch (e: Exception) {
                _state.postValue(RequestState.ERROR)
                e.printStackTrace()
            } catch (t: Throwable) {
                _state.postValue(RequestState.ERROR)
                t.printStackTrace()
            }
        }
    }

    fun saveUser(user: User)=prefRepository.saveUser(user)
    fun getTheUser()=prefRepository.getUser()
    fun deleteUser()=prefRepository.deleteUserFromPref()

    val genderList= arrayListOf<BaseCommonList>(
        BaseCommonList("Male", "Male"),
        BaseCommonList("Female", "Female"),
        BaseCommonList("Transgender", "Transgender"),
       // BaseCommonList("Transgender", "Trans Gender"),
       
    )

    fun genderText():String{
        return if (genderId=="Trans Gender")
            "Transgender"
        else genderId
    }

    fun saveCountryCode(code:String)=prefRepository.saveCountryCodeName(code)
    fun getCountryCodeNameFromPref()=prefRepository.getCountryCodeNameFromPref()

    fun removeCountryCodeFromPhoneNumber(number:String):String{
        var num:String=number
        if (number.contains(countryCode.toString())){
            num = number.removePrefix(countryCode.toString())
        }
        return num
    }
}