package com.lmslsbe.ui.main.dashboard.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmslsbe.data.FpapRepository
import com.lmslsbe.data.PrefRepository
import com.lmslsbe.model.DashboardDetail
import com.lmslsbe.model.RequestState
import com.lmslsbe.ui.common.isUrduMedium
import com.lmslsbe.utils.APP_TAG
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
    private var memberId=0
    var gender = ""

    init {
        classId=prefRepository.getUser()!!.memberInfo.classId
        gender=prefRepository.getUser()!!.memberInfo.gender
        memberId=prefRepository.getUser()!!.memberId
     getDashboardDetailData()
    }
    private fun getDashboardDetailData(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                _state.postValue(RequestState.LOADING)
                val response=repository.getDashboardDetail(memberId,54, subjectId, isUrduMedium)
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