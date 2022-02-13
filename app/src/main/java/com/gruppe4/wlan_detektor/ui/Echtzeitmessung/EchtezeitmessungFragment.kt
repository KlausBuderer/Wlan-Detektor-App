package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.net.wifi.SupplicantState
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentEchtzeitmessungBinding

class EchtezeitmessungFragment : Fragment() {

    private lateinit var echtzeitmessungViewModel: EchtzeitmessungViewModel
    private var _binding: FragmentEchtzeitmessungBinding? = null
    private val binding get() = _binding!!
    lateinit var dialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        echtzeitmessungViewModel =
            ViewModelProvider(this)[EchtzeitmessungViewModel::class.java]

        _binding = FragmentEchtzeitmessungBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dialog = Dialog(requireContext())

        //Pruefen ob die Berechtigung vorhanden ist
        if (isPermissionGranted(ACCESS_FINE_LOCATION)) {
            //Berechtigung vorhanden
        } else {
            // Erstellen eines Dialogs, um die Berechtigung zu erteilen
            dialog.setContentView(R.layout.berechtigung_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val okButton = dialog.findViewById<Button>(R.id.btn_einstellung)
            val abbrechenButton = dialog.findViewById<Button>(R.id.btn_abbrechen)

            okButton.setOnClickListener {
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity?.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    context?.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

                dialog.dismiss()
            }

            abbrechenButton.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "..Schade, leider können wir die SSID nicht ausgeben",
                    Toast.LENGTH_LONG
                ).show()

                echtzeitmessungViewModel.stopUpdateCoroutine()

                dialog.dismiss()
            }
        }



        val ssid: TextView = binding.tvSsid
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            if (it.supplicantState == SupplicantState.COMPLETED) {
                ssid.text = it.ssid.trim('"', '\"', '<', '>')
            } else {
                ssid.text = "-"
            }
        })


        val mac: TextView = binding.tvMac
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {

            if(it != null) {
                if (it.bssid != null) {
                    mac.text = it.bssid.uppercase()
                } else {
                    mac.text = "-"
                }
            }
        })



        val band: TextView = binding.tvFrequenz
        echtzeitmessungViewModel.band.observe(viewLifecycleOwner, Observer {
            if (it > 0.0) {
                band.text = it.toString() + " GHz"
            } else {
                band.text = "-"
            }
        })


        val updownspeed: TextView = binding.tvUpdownspeed
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            if(it.linkSpeed > 0 ) {
                updownspeed.text = it.linkSpeed.toString() + " Mbps"
            } else {
                updownspeed.text = "-"
            }
        })


        val progressBar: ProgressBar = binding.pgProgressBar
        echtzeitmessungViewModel.progressFarbe.observe(viewLifecycleOwner, Observer {
            //progressBar.progressTintList = ColorStateList.valueOf(it)

        })



        val textView: TextView = binding.tvSignalstaerkeWert
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            if (it.supplicantState == SupplicantState.COMPLETED) {
                textView.text = it.rssi.toString() + " dB"
                progressBar.progress = it.rssi
            } else {
                textView.text = "-"
                progressBar.progress = -127
            }
        })



        binding.netzwerkwahl.setOnClickListener {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }


        binding.tbtnStartEchtzeitmessung.setOnClickListener {

            //Starte zyklische Updates der Netzwerkdaten
            if (!binding.tbtnStartEchtzeitmessung.isChecked) {
                echtzeitmessungViewModel.startUpdateCoroutine()
            }
            //Stoppe zyklische Updates
            if (binding.tbtnStartEchtzeitmessung.isChecked) {
                echtzeitmessungViewModel.stopUpdateCoroutine()
                echtzeitmessungViewModel.stopSinus()

                binding.btnFloatingActionButton.setImageResource(R.drawable.ic_ton_aus)
            }
        }

        binding.btnFloatingActionButton.setOnClickListener {
            echtzeitmessungViewModel.tonEin = !echtzeitmessungViewModel.tonEin

            if (echtzeitmessungViewModel.tonEin) {
                echtzeitmessungViewModel.startSinus()
                binding.btnFloatingActionButton.setImageResource(R.drawable.ic_ton_an)
            } else {
                echtzeitmessungViewModel.stopSinus()
                binding.btnFloatingActionButton.setImageResource(R.drawable.ic_ton_aus)
            }

        }

        val seek = binding.seekBar
        seek?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                echtzeitmessungViewModel.frequenz = seek.progress
                Log.d("Seekbar", "${seek.progress}")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        return root
    }

    fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED

    override fun onResume() {
        super.onResume()

        echtzeitmessungViewModel.startUpdateCoroutine()
        binding.tbtnStartEchtzeitmessung.isChecked = false

        val hersteller: TextView = binding.tvHersteller
        echtzeitmessungViewModel.hersteller.observe(viewLifecycleOwner, Observer {
            if (echtzeitmessungViewModel.netzwerkInfo.value!!.supplicantState == SupplicantState.COMPLETED){
            if (it != null) {
                hersteller.text = it
            } else {
                hersteller.text = getString(R.string.txt_hersteller_unbekannt)
            }
            } else{
                hersteller.text = "-"
            }
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()

        //Beendet den Sinusgenerator bei einem Bildwechsel
        if (echtzeitmessungViewModel.getSinusJobStatus()) {
                echtzeitmessungViewModel.stopSinus()
        }
        //Beendet das Updaten der Netzwerkinformationen bei einem Bildwechsel
        if (echtzeitmessungViewModel.getUpdateJobStatus()) {
            echtzeitmessungViewModel.stopUpdateCoroutine()
        }
        _binding = null

    }
}


