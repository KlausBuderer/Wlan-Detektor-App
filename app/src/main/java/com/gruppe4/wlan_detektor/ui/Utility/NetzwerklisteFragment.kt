package com.gruppe4.wlan_detektor.ui.Utility

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.core.content.PermissionChecker.checkCallingPermission
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.NetzwerklisteFragmentBinding
import com.gruppe4.wlan_detektor.model.NetzwerkHandler

class NetzwerklisteFragment() : Fragment(), NetzwerkwahlAdapter.OnItemClickListener {

    // var netzwerkHandler: NetzwerkHandler = NetzwerkHandler()


    companion object {
        fun newInstance() = NetzwerklisteFragment()
    }

    var wifiManager: WifiManager = TODO()
    val it = getActivity()

    private lateinit var viewModel: NetzwerklisteViewModel
    var _binding: NetzwerklisteFragmentBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = NetzwerklisteFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root

        wifiManager = activity?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.startScan()


        val wifiScanReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                if (success) {
                  scanSuccess()
                    Toast.makeText(getActivity(), "Scan erfolgreich", Toast.LENGTH_SHORT).show()
                    Toast.makeText(getActivity(), wifiManager.scanResults.toString(), Toast.LENGTH_SHORT).show()
                } else {
                    scanFailure()
                }

            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        getActivity()?.registerReceiver(wifiScanReceiver, intentFilter)


        /*fun getResults() {
            var result = wifiManager.scanResults
            val adapter = NetzwerkwahlAdapter(result, this)
            binding?.rvnetzwerkliste?.adapter = adapter
            checkCallingOrSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }*/

        return root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(getActivity(), "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem: NetzwerkwahlItem = NetzwerkListe.netzwerkListe[position]
        clickedItem.verschluesselt = "Gedrückt"

        Navigation.findNavController(binding.root).navigate(
            R.id.action_netzwerkliste_to_navigation_echtzeitmessung
        )
    }

    private fun scanSuccess() {
       /* var result = wifiManager.scanResults
        val adapter = NetzwerkwahlAdapter(result, this)
        binding?.rvnetzwerkliste?.adapter = adapter
        checkCallingOrSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )*/

        //Toast.makeText(getActivity(), checkCallingOrSelfPermission(requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION), Toast.LENGTH_LONG).show()
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults

        Toast.makeText(getActivity(), "Scan nicht erfolgreich", Toast.LENGTH_SHORT).show()
    }

}


