package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentEchtzeitmessungBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class EchtezeitmessungFragment : Fragment() {

    private lateinit var echtzeitmessungViewModel: EchtzeitmessungViewModel
    private var _binding: FragmentEchtzeitmessungBinding? = null
    lateinit var wifiManager: WifiManager
    // This property is only valid between onCreateView and
    // onDestroyView

    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Pruefen ob die Berechtigung vorhanden ist
        if (isPermissionGranted(ACCESS_FINE_LOCATION)) {
            //Berechtigung vorhanden
        } else {
            val builder = AlertDialog.Builder(requireContext())
            //Dialog Titel
            builder.setTitle("Beachte")
            //Dialog Text
            builder.setMessage("Um die Informationen des Wlan Routers auslesen zu können muss die Lokalisierung erlaubt werden.")
            //Dialog Icon
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //Ja Button
            builder.setPositiveButton("Einstellungen") { dialogInterface, which ->
                try {
                    //context?.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS))
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity?.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    context?.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            }

            //Nein Button
            builder.setNegativeButton("Abbrechen"){dialogInterface, which ->
                Toast.makeText(requireContext(),"..Schade, leider können wir die SSID nicht ausgeben",Toast.LENGTH_LONG).show()
            }

            // Erstellen des Dialogs
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

            }




        echtzeitmessungViewModel =
            ViewModelProvider(this)[EchtzeitmessungViewModel::class.java]

        _binding = FragmentEchtzeitmessungBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val ssid: TextView = binding.tvSsid
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            ssid.text =  it.ssid})

        //TODO Applikation absturz
        /*val mac: TextView = binding.tvMac
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            mac.text =  it.bssid.toString()})*/

        val band: TextView = binding.tvFrequenz
        echtzeitmessungViewModel.band.observe(viewLifecycleOwner, Observer {
            band.text =  it.toString() + " GHz"})

        val supplier: TextView = binding.tvHersteller
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            supplier.text =  "noch nicht implementiert"})

        /*val sicherheitstyp: TextView = binding.tvSicherheittyp
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            sicherheitstyp.text =  it.currentSecurityType.toString()})*/

        val updownspeed: TextView = binding.tvUpdownspeed
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
        updownspeed.text =  it.linkSpeed.toString() + " Mbps"})

        val progressBar: ProgressBar = binding.pgProgressBar
        echtzeitmessungViewModel.progressFarbe.observe(viewLifecycleOwner, Observer {
            progressBar.progressTintList = ColorStateList.valueOf(it)

        })


        val textView: TextView = binding.tvSignalstaerkeWert


        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            textView.text = it.rssi.toString() + " dB"
            progressBar.progress = it.rssi
        })



        binding.netzwerkwahl.setOnClickListener {
        /*Navigation.findNavController(it).navigate(
            R.id.action_navigation_echtzeitmessung_to_netzwerkliste)*/
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

            binding.tbtnStartEchtzeitmessung.setOnClickListener {

                if(!binding.tbtnStartEchtzeitmessung.isChecked) {
                    lifecycleScope.launch {
                        echtzeitmessungViewModel.startUpdates()
                    }
                }
                if (binding.tbtnStartEchtzeitmessung.isChecked) {
                    lifecycleScope.cancel()
                }
            }

return root
}

    fun isPermissionGranted(permission:String):Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

    override fun onDestroyView() {
super.onDestroyView()
    //echtzeitmessungViewModel.startUpdates()
_binding = null
}
}


