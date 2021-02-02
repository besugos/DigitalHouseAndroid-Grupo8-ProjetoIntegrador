package com.besugos.marveluniverse.favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    var flag = MutableLiveData<Boolean>()

    fun setFlag() {
        flag.value = if(flag.value == null) true else !flag.value!!
    }

}