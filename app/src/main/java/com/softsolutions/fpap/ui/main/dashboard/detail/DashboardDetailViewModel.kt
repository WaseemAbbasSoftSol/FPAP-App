package com.softsolutions.fpap.ui.main.dashboard.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softsolutions.fpap.data.FpapRepository
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.model.DashboardDetail
import com.softsolutions.fpap.model.RequestState
import com.softsolutions.fpap.ui.common.isUrduMedium
import com.softsolutions.fpap.utils.APP_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardDetailViewModel(
    private val repository: FpapRepository,
    prefRepository: PrefRepository
):ViewModel() {
    private val _state=MutableLiveData<RequestState>()
    val state:LiveData<RequestState> = _state

    private val _dashboardData=MutableLiveData<DashboardDetail>()
    val dashboardData:LiveData<DashboardDetail> = _dashboardData

    private var classId=0
    var subjectId=0

    init {
        classId=prefRepository.getUser()!!.memberInfo.classId
     getDashboardDetailData()
    }
    private fun getDashboardDetailData(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _state.postValue(RequestState.LOADING)
                val response=repository.getDashboardDetail(classId, 758, isUrduMedium)
                if (response.isSuccessful){
                    response.body().let {
                        _dashboardData.postValue(it!!.data)
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
}