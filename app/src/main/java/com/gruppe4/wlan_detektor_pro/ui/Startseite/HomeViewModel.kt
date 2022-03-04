package com.gruppe4.wlan_detektor_pro.ui.Startseite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Startbild Viewmodel
 * @author Bruno Thurnherr
 * @since 1.0.0
 */
class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
