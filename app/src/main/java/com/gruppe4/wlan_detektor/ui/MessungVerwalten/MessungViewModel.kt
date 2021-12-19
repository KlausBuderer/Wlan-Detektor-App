package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessungViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Messungen verwalten"
    }
    val text: LiveData<String> = _text
}