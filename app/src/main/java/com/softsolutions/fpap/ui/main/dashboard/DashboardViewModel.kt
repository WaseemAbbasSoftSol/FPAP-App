package com.softsolutions.fpap.ui.main.dashboard

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softsolutions.fpap.data.FpapRepository
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.model.CountedUsers
import com.softsolutions.fpap.model.RequestState
import com.softsolutions.fpap.model.SubjectList
import com.softsolutions.fpap.model.account.User
import com.softsolutions.fpap.ui.common.isNewStudentRegistering
import com.softsolutions.fpap.ui.common.isUrduMedium
import com.softsolutions.fpap.utils.APP_TAG
import com.softsolutions.fpap.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: FpapRepository,
    prefRepository: PrefRepository
) : ViewModel() {
    private val _state = MutableLiveData<RequestState>()
    val state: LiveData<RequestState> = _state

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _subjects = MutableLiveData<List<SubjectList>>()
    val subjects: LiveData<List<SubjectList>> = _subjects

    private val _countedUser = MutableLiveData<CountedUsers>()
    val countedUser: LiveData<CountedUsers> = _countedUser

    private val _updateLanguageMsg = MutableLiveData<String>()
    val updateLanguageMsg: LiveData<String> = _updateLanguageMsg

    private val _updateTrasnsgenderSubjectMsg = SingleLiveEvent<String>()
    val updateTrasnsgenderSubjectMsg: LiveData<String> = _updateTrasnsgenderSubjectMsg

    private var memberId = 0
    var gender=""

    init {
        memberId = prefRepository.getUser()!!.memberId
        gender=prefRepository.getUser()!!.memberInfo.gender
        _subjects.value= emptyList()
       // if (!isNewStudentRegistering) getDashboardData()
        getDashboardData()
        getAllCountedUsers()
    }

    fun getDashboardData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                val response = repository.getDashboardData(memberId)
                if (response.isSuccessful) {
                    response.body().let {
                        _user.postValue(it!!.data)
                        var tempList= arrayListOf<SubjectList>()
                        tempList.addAll(it.data.subjectList)
                        if (gender=="Male                                              "){
                            for ((i,value )in tempList.withIndex()){
                                if (value.id==765){
                                    tempList.remove(value)
                                    break
                                }
                            }
                        }else if (gender=="Trans Gender                                      "){
                            for ((i,value )in tempList.withIndex()){
                                if (value.id==765){
                                    tempList.remove(value)
                                    break
                                }
                            }
                        }
                        else{
                            for ((i,value )in tempList.withIndex()){
                                if (value.id==762){
                                    tempList.remove(value)
                                    break
                                }
                            }
                        }

                        _subjects.postValue(tempList)
                       // isUrduMedium=it!!.data.memberInfo.isUrduMedium
                    }
                } else response.errorBody().let {
                    Log.d(APP_TAG, it!!.string())
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

    fun updateTransgenderSubject(subjectId:Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _state.postValue(RequestState.LOADING)
                val response=repository.updateTransgenderSubject(memberId, subjectId)
                if (response.isSuccessful){
                    response.body().let {
                        _updateTrasnsgenderSubjectMsg.postValue(it!!.data.message)
                    }
                }else response.errorBody().let {
                  Log.d(APP_TAG, it!!.string())
                    }
                _state.postValue(RequestState.DONE)
            }catch (e:Exception){
                _state.postValue(RequestState.ERROR)
                e.printStackTrace()
            }catch (t:Throwable){
                _state.postValue(RequestState.ERROR)
                t.printStackTrace()
            }
        }
    }

    private fun getAllCountedUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                val response = repository.getCountedUsers()
                if (response.isSuccessful) {
                    response.body().let {
                        _countedUser.postValue(it!!.data)
                    }
                } else response.errorBody().let {
                    Log.d(APP_TAG, it!!.string())
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

    fun updateLanguage(isUrduMediumSelected:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                val response = repository.updateLanguage(memberId, isUrduMediumSelected)
                if (response.isSuccessful) {
                    response.body().let {
                        _updateLanguageMsg.postValue(it!!.responseMessage)
                    }
                } else response.errorBody().let {
                    _updateLanguageMsg.postValue(it!!.string())
                    Log.d(APP_TAG, it!!.string())
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
}