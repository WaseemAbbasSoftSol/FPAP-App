package com.softsolutions.fpap.ui.main.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softsolutions.fpap.data.FpapRepository
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.model.RequestState
import com.softsolutions.fpap.model.account.User
import com.softsolutions.fpap.model.common.BaseCommonList
import com.softsolutions.fpap.utils.APP_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: FpapRepository,
    prefRepository: PrefRepository
):ViewModel() {

    private val _state = MutableLiveData<RequestState>()
    val state: LiveData<RequestState> = _state

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _qualifiationList = MutableLiveData<List<BaseCommonList>>()
    val qualificationList: LiveData<List<BaseCommonList>> = _qualifiationList

    private val _regionList = MutableLiveData<List<BaseCommonList>>()
    val regionList: LiveData<List<BaseCommonList>> = _regionList

    private val _citiesList = MutableLiveData<List<BaseCommonList>>()
    val citiesList: LiveData<List<BaseCommonList>> = _citiesList

    var qualificationId=0
    var regionId=0
    var cityId=0

    init {
        _qualifiationList.value = emptyList()
        _regionList.value = emptyList()
        _citiesList.value = emptyList()
        _user.value=prefRepository.getUser()!!
        qualificationId=prefRepository.getUser()!!.memberInfo.qualificationId
        regionId=prefRepository.getUser()!!.memberInfo.regionId
        cityId=prefRepository.getUser()!!.memberInfo.cityId
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


}