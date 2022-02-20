package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor_pro.model.Datenbank.RepositoryDb
import java.io.IOException

class MessungListeViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val LOG_TAG: String = "MessungsListe: "

    private val repositoryDb: RepositoryDb = RepositoryDb(application)

    private val _messungsliste = MutableLiveData<List<TblMessung>?>().apply {
    }

    val messungsliste: LiveData<List<TblMessung>?> = _messungsliste

    suspend fun getAlleMessungen() {
        try {
            val alleMessungen = repositoryDb.queryMessung()
            Log.e(LOG_TAG, "Query erfolgreich")
            _messungsliste.postValue(alleMessungen)
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Query nicht erfolgreich")
        }
    }

    suspend fun deleteMessung(name: String) {
        try {

            repositoryDb.deleteMessung(repositoryDb.getMessung(name))

            Log.e(LOG_TAG, "Query erfolgreich")
            getAlleMessungen()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Query nicht erfolgreich")
        }
    }
}
