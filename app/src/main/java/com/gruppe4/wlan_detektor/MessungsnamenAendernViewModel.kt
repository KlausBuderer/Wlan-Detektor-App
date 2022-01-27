package com.gruppe4.wlan_detektor

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gruppe4.wlan_detektor.model.Datenbank.RepositoryDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessungsnamenAendernViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryDb: RepositoryDb = RepositoryDb(application)
    var result: Int = 0
    var konditionNamenValide = false

    private val _nameValide = MutableLiveData<Boolean>().apply {
        konditionNamenValide = value == true
    }
    var nameValide: LiveData<Boolean> = _nameValide

    //Speicherung des neuen Messungsnamen
    fun namenUpdate(aktuellerName: String, neuerName: String){
        repositoryDb.updateMessungsNamen(aktuellerName,neuerName)
    }

    //Funktion die den eingegebenen Namen in der Datenbank auf Redundanz prüft
    suspend fun namenValidieren(eingabe: String){
        result = repositoryDb.namenPruefen(eingabe)
        _nameValide.postValue( result == 0)
        Log.d("Routine","MessungsId: $result")
    }

    //Coroutine zur Speicherung des neuen Messungsnamen
    fun namenValidierenRoutine(eingabe: String){
        val scope = CoroutineScope(Dispatchers.IO).launch {
            namenValidieren(eingabe)
        }
    }
}