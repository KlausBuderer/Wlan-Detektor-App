package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Messung Verwalten Viewmodel
 * @author Klaus Buderer
 * @since 1.0.0
 */
class MessungViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Messungen verwalten"
    }
    val text: LiveData<String> = _text
}