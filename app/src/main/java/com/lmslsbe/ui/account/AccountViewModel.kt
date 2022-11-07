package com.lmslsbe.ui.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmslsbe.data.FpapRepository
import com.lmslsbe.data.PrefRepository
import com.lmslsbe.model.RequestState
import com.lmslsbe.model.account.ForgotPassword
import com.lmslsbe.model.account.Login
import com.lmslsbe.model.account.Register
import com.lmslsbe.model.account.User
import com.lmslsbe.model.common.BaseCommonList
import com.lmslsbe.utils.APP_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(
    private val repository: FpapRepository,
    private val prefRepository: PrefRepository
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

    init {
        _qualifiationList.value = emptyList()
        _regionList.value = emptyList()
        _citiesList.value = emptyList()
        getQualificationLists()
        getRegionLists()
        getCitiesLists()
    }

    fun register(register: Register) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                val response = repository.register(register)
                if (response.isSuccessful) {
                    response.body().let {
                        _user.postValue(it!!.data)
                        _message.postValue(it.responseMessage)
                        _errorMessage.postValue(it.errorMessage)
                    }
                } else {
                    response.errorBody().let {
                        Log.d(APP_TAG, it!!.string())
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

    fun login(login: Login) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                val response = repository.login(login)
                if (response.isSuccessful) {
                    response.body().let {
                        _user.postValue(it!!.data)
                        _message.postValue(it.responseMessage)
                        _errorMessage.postValue(it.errorMessage)
                    }
                } else {
                    response.errorBody().let {
                        Log.d(APP_TAG, it!!.string())
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

    fun recoverForgotPassword(email: ForgotPassword) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                val response = repository.forgotPassword(email)
                if (response.isSuccessful) {
                    response.body().let {
                        _message.postValue(it!!.responseMessage)
                        _errorMessage.postValue(it.errorMessage)
                    }
                } else {
                    response.errorBody().let {
                        Log.d(APP_TAG, it!!.string())
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

    fun saveUser(user: User)=prefRepository.saveUser(user)
    fun getTheUser()=prefRepository.getUser()
    fun deleteUser()=prefRepository.deleteUserFromPref()

    val genderList= arrayListOf<BaseCommonList>(
        BaseCommonList("Male", "Male"),
        BaseCommonList("Female", "Female"),
        BaseCommonList("Transgender", "Transgender")
    )

    fun saveCountryCode(code:String)=prefRepository.saveCountryCodeName(code)
}