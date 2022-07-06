package com.softsolutions.fpap.ui.main.mcqs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softsolutions.fpap.data.FpapRepository
import com.softsolutions.fpap.data.PrefRepository
import com.softsolutions.fpap.model.RequestState
import com.softsolutions.fpap.model.mcq.Mcq
import com.softsolutions.fpap.utils.APP_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class McqsViewModel(
    private val repository: FpapRepository,
    prefRepository: PrefRepository
):ViewModel() {

    private val _state = MutableLiveData<RequestState>()
    val state: LiveData<RequestState> = _state

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _mcq = MutableLiveData<List<Mcq>>()
    val mcq: LiveData<List<Mcq>> = _mcq

    var unitId=0

    init {
        _mcq.value= emptyList()
        getMcqsList()
    }

    private fun getMcqsList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                val response = repository.getMcqsList(3800)
                if (response.isSuccessful) {
                    response.body().let {
                        _mcq.postValue(it!!.data)
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
}