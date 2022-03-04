import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Datenzugriffsobjekt.WlanDetektorDao
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor_pro.model.Datenbank.RepositoryDb
import com.gruppe4.wlan_detektor_pro.model.Datenbank.WlanDetektorDb
import kotlinx.coroutines.coroutineScope
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import java.io.IOException

@Ignore
@RunWith(JUnit4::class)
class DatenbankTest {
    private lateinit var dao: WlanDetektorDao
    private lateinit var db: WlanDetektorDb

    val messungsListe = listOf<TblMessung>(
        TblMessung("Test1","Test1",1,"",""),
        TblMessung("Test2","Test2",2,"",""),
        TblMessung("Test3","Test3",0,"",""),
        TblMessung("Test4","Test4",1,"",""),
    )

    @Before
    fun createDb() {
        val context =
        ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WlanDetektorDb::class.java).build()
        dao = db.wlanDetektorDao
    }
    val application = Mockito.mock(Application::class.java)
    private val repositoryDb: RepositoryDb = RepositoryDb(application)
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun speichereMessungenTest() {
        messungsListe.forEach() {
            repositoryDb.insertMessung(it)
        }
    }

    @Test
    @Throws(Exception::class)
    suspend fun leseMessungenTest() {
        val messungen = coroutineScope() {
             repositoryDb.getMessung("Test1")
        }
        assertThat(messungen.ssid).isEqualTo("Test1")
    }
}