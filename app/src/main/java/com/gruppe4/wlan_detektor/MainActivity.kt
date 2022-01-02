package com.gruppe4.wlan_detektor

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.net.wifi.WifiManager
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gruppe4.wlan_detektor.databinding.ActivityMainBinding
import com.gruppe4.wlan_detektor.model.Datenbank.RepositoryDb
import com.gruppe4.wlan_detektor.model.Datenbank.WlanDetektorDb
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbInit = RepositoryDb(application)
        MainScope().launch {
            dbInit.queryMessung()
        }

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val navView: BottomNavigationView = binding.navView

            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home, R.id.navigation_echtzeitmessung, R.id.navigation_messung, R.id.navigation_Visualisierung
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
