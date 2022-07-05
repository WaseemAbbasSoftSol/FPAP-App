package com.softsolutions.fpap.ui.main.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softsolutions.fpap.data.FpapRepository
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.model.RequestState
import com.softsolutions.fpap.model.account.User
import com.softsolutions.fpap.utils.APP_TAG
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

    private var memberId = 0

    init {
        memberId = prefRepository.getUser()!!.memberId
        getDashboardData()
    }

    private fun getDashboardData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                val response = repository.getDashboardData(memberId)
                if (response.isSuccessful) {
                    response.body().let {
                        _user.postValue(it!!.data)
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
}