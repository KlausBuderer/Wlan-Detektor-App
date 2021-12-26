package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.content.Intent
import android.content.res.ColorStateList
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentEchtzeitmessungBinding
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
        echtzeitmessungViewModel =
            ViewModelProvider(this).get(EchtzeitmessungViewModel::class.java)

        _binding = FragmentEchtzeitmessungBinding.inflate(inflater, container, false)
        val root: View = binding.root

        echtzeitmessungViewModel.startUpdates()

        val ssid: TextView = binding.tvSsid
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            ssid.text =  it.ssid})

        /*val mac: TextView = binding.tvMac
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            mac.text =  it.macAddress.toString()})*/

        val band: TextView = binding.tvFrequenz
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            band.text =  it.frequency.toString()})

        val supplier: TextView = binding.tvHersteller
        echtzeitmessungViewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {
            supplier.text =  it.bssid})

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



        binding.netzwerkwahl.setOnClickListener {
        /*Navigation.findNavController(it).navigate(
            R.id.action_navigation_echtzeitmessung_to_netzwerkliste)*/
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

})
return root
}

override fun onDestroyView() {
super.onDestroyView()
_binding = null
}
}


