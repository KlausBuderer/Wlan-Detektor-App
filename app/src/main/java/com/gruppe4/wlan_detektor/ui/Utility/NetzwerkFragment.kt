package com.gruppe4.wlan_detektor.ui.Utility

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gruppe4.wlan_detektor.databinding.FragmentNetzwerkwahlBinding
import com.gruppe4.wlan_detektor.databinding.FragmentVisualisierungBinding
import com.gruppe4.wlan_detektor.ui.Echtzeitmessung.EchtezeitmessungFragment

class NetzwerkFragment(netzwerkArray: ArrayList<NetzwerkwahlItem>) : Fragment() {

    private lateinit var _binding: FragmentNetzwerkwahlBinding
    private val netzwerkArray = netzwerkArray

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNetzwerkwahlBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.lvNetzwerkwahl.isClickable = true
        binding.lvNetzwerkwahl.adapter = NetzwerkwahlAdapter(Activity(),netzwerkArray)
        binding.lvNetzwerkwahl.setOnItemClickListener { parent, view, position, id ->

            val ssid = netzwerkArray[position].ssid
            val icon = netzwerkArray[position].icon
            val verschluesselung = netzwerkArray[position].verschluesselt



}
        return root
}

}