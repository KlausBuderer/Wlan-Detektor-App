package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.ui.Utility.NetzwerkFragment
import com.gruppe4.wlan_detektor.ui.Utility.NetzwerkwahlItem

class EchtzeitmessungViewModel : ViewModel() {

    private lateinit var netzwerkArrayList : ArrayList<NetzwerkwahlItem>

    private val _text = MutableLiveData<String>().apply {
        value = "-50 dB"
    }



    val text: LiveData<String> = _text

    fun netzwerkwahl(){


    }
}