package com.gruppe4.wlan_detektor.ui.Visualisierung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VisualisierungViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Visualisierung Fragment"
    }
    val text: LiveData<String> = _text
}