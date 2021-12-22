package com.gruppe4.wlan_detektor

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gruppe4.wlan_detektor.databinding.FragmentNetzwerkwahlBinding
import com.gruppe4.wlan_detektor.databinding.NetzwerklisteFragmentBinding
import com.gruppe4.wlan_detektor.ui.Utility.NetzwerkwahlAdapter
import com.gruppe4.wlan_detektor.ui.Utility.NetzwerkwahlItem

class NetzwerklisteFragment() : Fragment() {


    companion object {
        fun newInstance() = NetzwerklisteFragment()
    }

    private lateinit var viewModel: NetzwerklisteViewModel

    private lateinit var _binding: NetzwerklisteFragmentBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.netzwerkliste_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NetzwerklisteViewModel::class.java)
        // TODO: Use the ViewModel

        var netzwerkArray = viewModel.getNetwork()


        binding.lvNetzwerkwahl.isClickable = true
        binding.lvNetzwerkwahl.adapter = NetzwerkwahlAdapter(Activity(), netzwerkArray)
        binding.lvNetzwerkwahl.setOnItemClickListener { parent, view, position, id ->

            val ssid = netzwerkArray[position].ssid
            val icon = netzwerkArray[position].icon
            val verschluesselung = netzwerkArray[position].verschluesselt

        }

    }
}