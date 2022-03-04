package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.FragmentMessungenBinding

enum class MESSUNGLISTE_KONTEXT {Bearbeiten, Loeschen, Visualisierung}

/**
 * Messung Verwalten View
 * @author Klaus Buderer
 * @since 1.0.0
 */
class MessungFragment : Fragment() {

    private lateinit var messungViewModel: MessungViewModel
    private var _binding: FragmentMessungenBinding? = null

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
        binding.cvMessungHinzu.setOnClickListener {
            Navigation.findNavController(it).navigate(
                R.id.action_navigation_messung_to_messungHinzufuegen
            )
        }
            return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Absprung in eine Liste von Messungen
        binding.cvMessungBearbeiten.setOnClickListener{
            val action =
                MessungFragmentDirections.actionNavigationMessungToMessungListeFragment(
                    MESSUNGLISTE_KONTEXT.Bearbeiten.toString()
                )

            Navigation.findNavController(binding.root).navigate(action)
        }

        //Absprung in eine Liste von Messungen
        binding.cvMessungLoeschen.setOnClickListener{
            val action =
                MessungFragmentDirections.actionNavigationMessungToMessungListeFragment(
                    MESSUNGLISTE_KONTEXT.Loeschen.toString()
                )

            Navigation.findNavController(binding.root).navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}