package com.gruppe4.wlan_detektor_pro

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gruppe4.wlan_detektor_pro.databinding.ActivityMainBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.RepositoryDb
import com.gruppe4.wlan_detektor_pro.ui.Echtzeitmessung.EchtezeitmessungFragmentDirections
import com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.*
import com.gruppe4.wlan_detektor_pro.ui.Startseite.HomeFragmentDirections
import com.gruppe4.wlan_detektor_pro.ui.Utility.DIALOG_KONTEXT.*
import com.gruppe4.wlan_detektor_pro.ui.Visualisierung.VisuDetailFragmentDirections
import com.gruppe4.wlan_detektor_pro.ui.Visualisierung.VisuFullScreenBildDirections
import com.gruppe4.wlan_detektor_pro.ui.Visualisierung.VisualisierungFragmentDirections
import com.gruppe4.wlan_detektor_pro.ui.Visualisierung.Visualisierung_Grid_FragmentDirections
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Main Activity
 * Einziges Activity in diesem Projekt.\n
 * Die Actionbar und die Navigation wird in dieser Klasse
 * realisiert.
 * @author Klaus Buderer
 * @since 1.0.0
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var activFragment: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val dbInit = RepositoryDb(application)
        MainScope().launch {
            dbInit.queryMessung()
        }

        supportActionBar!!.elevation = 0f
        supportActionBar!!.setBackgroundDrawable(getDrawable(R.drawable.dark_background))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        activFragment = navController.currentDestination?.id ?: 0

        // Uebergabe der Navigation IDs -> Navigationsleiste
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_echtzeitmessung,
                R.id.navigation_messung,
                R.id.navigation_Visualisierung
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hilfe_button, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        activFragment = navController.currentDestination?.id!!

        if (item.itemId == R.id.action_help) {
            callHilfeDialog(activFragment) //Aufruf von Hilfedialog abhängig des aktiven Fragments
        } else if (item.itemId == R.id.action_about_us) {
            navController.navigate(R.id.ueberUns2)//Aufruf Ueberuns Modal
        } else if (item.itemId == R.id.action_terms) {
            navController.navigate(
                R.id.terms_conditions
            )//Aufruf Terms Conditions
        } else if (item.itemId == R.id.action_datenschutz) {
            navController.navigate(R.id.datenschutzFragment)//Aufruf Disclaimer
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun hideStatusBar() {
        if (Build.VERSION.SDK_INT < 30) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        } else {
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    /**
     * Aufruf des Hilfedialogs entsprechend des geöffneten Fragments
     * @author Klaus Buderer
     * @since 1.0.0
     * @param fragment Navigations-ID des aktuellen Fragments
     */
    fun callHilfeDialog(fragment: Int) {
        when (fragment) {

            R.id.navigation_echtzeitmessung ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    EchtezeitmessungFragmentDirections.actionNavigationEchtzeitmessungToDialogFragment(
                        ECHTZEITMESSUNG
                    )
                )

            R.id.navigation_messung ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    MessungFragmentDirections.actionNavigationMessungToDialogFragment(
                        MESSUNG_VERWALTEN
                    )
                )

            R.id.messungListeFragment ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    MessungListeFragmentDirections.actionMessungListeFragmentToDialogFragment(
                        MESSUGSLISTE
                    )
                )

            R.id.messungBearbeitenFragment ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    MessungBearbeitenFragmentDirections.actionMessungBearbeitenFragmentToDialogFragment(
                        MESSUNG_BEARBEITEN
                    )
                )

            R.id.messungHinzufuegen ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    MessungHinzufuegenDirections.actionMessungHinzufuegenToDialogFragment(
                        MESSUNG_HINZUFUEGEN
                    )
                )

            R.id.messpunktErfassungsFragment ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    MesspunktErfassungsFragmentDirections.actionMesspunktErfassungsFragmentToDialogFragment(
                        MESSPUNKT_ERFASSEN
                    )
                )

            R.id.navigation_home ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    HomeFragmentDirections.actionNavigationHomeToDialogFragment(HOME)
                )

            R.id.navigation_Visualisierung ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    VisualisierungFragmentDirections.actionNavigationVisualisierungToDialogFragment(
                        MESSUGSLISTE
                    )
                )

            R.id.visualisierung_Grid_Fragment ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    Visualisierung_Grid_FragmentDirections.actionVisualisierungGridFragmentToDialogFragment(
                        VISUALISIERUNG_GRID
                    )
                )

            R.id.visuDetailFragment ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    VisuDetailFragmentDirections.actionVisuDetailFragmentToDialogFragment(
                        VISUALISIERUNG_DETAIL
                    )
                )
            R.id.visuFullScreenBild ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    VisuFullScreenBildDirections.actionVisuFullScreenBildToDialogFragment(
                        VISULISIERUNG_FULLSCREEN_BILD
                    )
                )
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}

