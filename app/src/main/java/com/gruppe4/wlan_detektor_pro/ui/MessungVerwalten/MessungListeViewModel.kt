package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor_pro.model.Datenbank.RepositoryDb
import java.io.IOException

/**
 * Messungsliste Viewmodel
 * @author Klaus Buderer
 * @since 1.0.0
 */
class MessungListeViewModel(application: Application) : AndroidViewModel(application) {

    private val LOG_TAG: String = "MessungsListe: "
    private val repositoryDb: RepositoryDb = RepositoryDb(application)
    private val _messungsliste = MutableLiveData<List<TblMessung>?>().apply{
    }

    val messungsliste: LiveData<List<TblMessung>?> = _messungsliste

    /**
     * Liesst alle Messungen der Datenbank
     * @author Klaus Buderer
     * @since 1.0.0
     */
    suspend fun getAlleMessungen(){
        try {
            val alleMessungen = repositoryDb.queryMessung()
            Log.e(LOG_TAG, "Query erfolgreich")
            _messungsliste.postValue(alleMessungen)
        }catch (e: IOException){
            Log.e(LOG_TAG, "Query nicht erfolgreich")
        }
    }

    /**
     * Löscht Messung aus der Datenbank
     * @author Klaus Buderer
     * @since 1.0.0
     */
     suspend fun deleteMessung(name:String){
        try {
            repositoryDb.deleteMessung(repositoryDb.getMessung(name))
            Log.d(LOG_TAG, "Query erfolgreich")
            getAlleMessungen()
        }catch (e: IOException){
            Log.e(LOG_TAG, "Query nicht erfolgreich")
        }
    }
}