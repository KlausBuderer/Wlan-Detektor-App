package com.gruppe4.wlan_detektor

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.internal.ContextUtils.getActivity
import com.gruppe4.wlan_detektor.databinding.ActivityMainBinding
import com.gruppe4.wlan_detektor.model.Datenbank.RepositoryDb
import com.gruppe4.wlan_detektor.model.Datenbank.WlanDetektorDb
import com.gruppe4.wlan_detektor.ui.Echtzeitmessung.EchtezeitmessungFragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var activFragment: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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

            Log.e("activFragment: ","${activFragment}")
        Log.e("Fragment: ","${R.id.navigation_echtzeitmessung}")
        Log.e("Activity: ","${R.layout.activity_main}")

        //Aufruf von Hilfedialog abhängig des aktiven Fragments
        if(item.itemId == R.id.action_help) {
            callHilfeDialog(activFragment)
        }else if (item.itemId == R.id.action_about_us){
            navController.navigate(R.id.ueberUns2)
        }else if(item.itemId == R.id.action_terms){
            navController.navigate(R.id.terms_conditions
            )
        }else if(item.itemId == R.id.action_datenschutz){
            navController.navigate(R.id.datenschutzFragment)
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

    fun callHilfeDialog(fragment: Int){
        when(fragment){
            R.id.navigation_echtzeitmessung ->
                findNavController(R.id.nav_host_fragment_activity_main).navigate(
                    R.id.action_navigation_echtzeitmessung_to_echtzeitDialogFragment)
        }
    }

}

