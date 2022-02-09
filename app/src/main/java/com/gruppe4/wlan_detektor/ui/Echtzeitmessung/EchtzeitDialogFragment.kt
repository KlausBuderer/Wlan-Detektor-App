package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentEchtzeitDialogBinding
import com.gruppe4.wlan_detektor.ui.Utility.DIALOG_KONTEXT
import com.gruppe4.wlan_detektor.ui.Utility.HilfeAdapter
import com.gruppe4.wlan_detektor.ui.Visualisierung.MesspunktVisuAdapter

data class Hilfe(val videoPfade: Uri, val titel: String, val beschreibungen: String)

class EchtzeitDialogFragment : BottomSheetDialogFragment(), HilfeAdapter.OnItemClickListener {

    private var _binding: FragmentEchtzeitDialogBinding? = null
    private val binding get() = _binding!!
    val args: EchtzeitDialogFragmentArgs by navArgs()
    private var hilfeListe = listOf<Hilfe>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireDialog().dismiss()

        _binding = FragmentEchtzeitDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (args.kontext == DIALOG_KONTEXT.ECHTZEITMESSUNG){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")
            val hilfe2 = Hilfe(uri2,resources.getString(R.string.txt_untertitel_2_echtzeitmessung),"So erstellen sie eine Echtzeitmessung")

            binding.title.text = getString(R.string.txt_titel_home_echzeitmessung)
            binding.beschreibung.text = getString(R.string.txt_beschreibung_echzeitmessung)

            hilfeListe = listOf(hilfe, hilfe2)


        }
        if (args.kontext == DIALOG_KONTEXT.HOME){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")

            binding.title.text = "Home"
            binding.beschreibung.text = getString(R.string.txt_beschreibung_echzeitmessung)

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.MESSUNG_VERWALTEN){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")

            binding.title.text = getString(R.string.txt_verwalte_deine_messungen)
            binding.beschreibung.text = getString(R.string.txt_beschreibung_echzeitmessung)

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.MESSUNG_BEARBEITEN){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")

            binding.title.text = "Home"
            binding.beschreibung.text = getString(R.string.txt_beschreibung_echzeitmessung)

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.MESSUNG_HINZUFUEGEN){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")

            binding.title.text = getString(R.string.txt_messung_hinzufuegen)
            binding.beschreibung.text = getString(R.string.txt_beschreibung_echzeitmessung)

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.MESSUGSLISTE){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")

            binding.title.text = getString(R.string.txt_messungsliste)
            binding.beschreibung.text = getString(R.string.txt_beschreibung_echzeitmessung)

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.MESSPUNKT_ERFASSEN){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")

            binding.title.text = getString(R.string.txt_messpunkt_hinzufuegen)
            binding.beschreibung.text = getString(R.string.txt_beschreibung_echzeitmessung)

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.MESSUNG_LOESCHEN){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")

            binding.title.text = getString(R.string.txt_messung_loeschen)
            binding.beschreibung.text = getString(R.string.txt_beschreibung_echzeitmessung)

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.VISUALISIERUNG_GRID){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")

            binding.title.text = getString(R.string.title_visualisierung_de)
            binding.beschreibung.text = getString(R.string.txt_beschreibung_visualisierung)

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.VISUALISIERUNG_DETAIL){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")

            binding.title.text = getString(R.string.txt_titel_home_visualisierung)
            binding.beschreibung.text = getString(R.string.txt_beschreibung_visualisierung)

            hilfeListe = listOf(hilfe)
        }
        if (args.kontext == DIALOG_KONTEXT.VISULISIERUNG_FULLSCREEN_BILD){

            val uri1 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.netzwahl)
            val uri2 = Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung)

            val hilfe = Hilfe(uri1,resources.getString(R.string.txt_wlan_anmeldung),"So wählen sie ihr Netzwerk")

            binding.title.text = "Home"
            binding.beschreibung.text = getString(R.string.txt_beschreibung_echzeitmessung)

            hilfeListe = listOf(hilfe)
        }

        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvHilfe.layoutManager = LinearLayoutManager(activity?.applicationContext)
        val adapter = HilfeAdapter(hilfeListe,this.requireContext(),this)
        binding?.rvHilfe?.adapter = adapter
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onItemClick(position: Int) {

    }

}