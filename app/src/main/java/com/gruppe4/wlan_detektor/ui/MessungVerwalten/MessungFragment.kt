package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentMessungenBinding


class MessungFragment : Fragment() {

    private lateinit var messungViewModel: MessungViewModel
    private var _binding: FragmentMessungenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        messungViewModel =
            ViewModelProvider(this).get(MessungViewModel::class.java)

        _binding = FragmentMessungenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Absprung in Maske um neue Messung hinzuzufügen
        binding.btnAddMessung.setOnClickListener {
            Navigation.findNavController(it).navigate(
                R.id.action_navigation_messung_to_messungHinzufuegen
            )
        }
        //Absprung in eine Liste von Messungen
        binding.btnEditMessung.setOnClickListener{
            Navigation.findNavController(it).navigate(
                R.id.action_navigation_messung_to_messungListeFragment
            )
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}