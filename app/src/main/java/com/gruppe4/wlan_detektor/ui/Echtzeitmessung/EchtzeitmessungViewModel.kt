package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EchtzeitmessungViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "-50 dB"
    }
    val text: LiveData<String> = _text

    fun netzwerkwahl(){
        println("Netzwerk wählen")
    }
}