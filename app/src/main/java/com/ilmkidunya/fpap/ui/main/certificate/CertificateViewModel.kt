package com.ilmkidunya.fpap.ui.main.certificate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilmkidunya.fpap.data.FpapRepository
import com.ilmkidunya.fpap.data.PrefRepository
import com.ilmkidunya.fpap.model.Certificates
import com.ilmkidunya.fpap.model.RequestState
import com.ilmkidunya.fpap.utils.APP_TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CertificateViewModel(
    private val repository: FpapRepository,
    prefRepository: PrefRepository
):ViewModel() {
    private val _state = MutableLiveData<RequestState>()
    val state: LiveData<RequestState> = _state

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _certificate = MutableLiveData<Certificates>()
    val certificate: LiveData<Certificates> = _certificate

    private var memberId=0

    init {
        memberId=prefRepository.getUser()!!.memberId
        getCourseCertificate()
    }
    private fun getCourseCertificate() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(RequestState.LOADING)
                val response = repository.getCourseCertificate(memberId)
                if (response.isSuccessful) {
                    response.body().let {
                        _certificate.postValue(it!!.data)
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