package com.gruppe4.wlan_detektor_pro.ui.Startseite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.FragmentHomeBinding
import com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MESSUNGLISTE_KONTEXT

/**
 * Startbild View
 * @author Bruno Thurnherr
 * @since 1.0.0
 */
class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Navigation in die Echtzeitmessung
        binding.cvEchtzeitmessung.setOnClickListener {
            Navigation.findNavController(it).navigate(
                R.id.action_navigation_home_to_navigation_echtzeitmessung
            )
        }
        //Navigation in das Messung hinzufügen Bild
        binding.cvMessungVerwaltung.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_navigation_home_to_messungHinzufuegen)
        }
        //Navigation in das Messung bearbeiten Bild
        binding.cvMessungBearbeiten.setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavigationHomeToMessungListeFragment(
                    MESSUNGLISTE_KONTEXT.Bearbeiten.toString()
                )
            Navigation.findNavController(binding.root).navigate(action)
        }

        //Navigation in die Visualisierung
        binding.cvVisualisierung.setOnClickListener {
            Navigation.findNavController(it).navigate(
                R.id.action_navigation_home_to_navigation_Visualisierung
            )
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
    }
}