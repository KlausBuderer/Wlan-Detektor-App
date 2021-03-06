package com.gruppe4.wlan_detektor_pro.ui.Echtzeitmessung

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.FragmentDialogBinding
import com.gruppe4.wlan_detektor_pro.ui.Utility.DIALOG_KONTEXT
import com.gruppe4.wlan_detektor_pro.ui.Utility.HilfeAdapter

data class Hilfe(val videoPfade: Uri, val titel: String, val beschreibungen: String)

/**
 * Hilfe Dialog View
 * @author Klaus Buderer
 * @since 1.0.0
 */
class DialogFragment : BottomSheetDialogFragment(), HilfeAdapter.OnItemClickListener {

    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!
    val args: DialogFragmentArgs by navArgs()
    private var hilfeListe = listOf<Hilfe>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireDialog().dismiss()

        _binding = FragmentDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (args.kontext == DIALOG_KONTEXT.ECHTZEITMESSUNG) {

            val uri1 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.em_messung)
            val uri2 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.em_anmeldung)
            val uri3 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.em_berechtigung)

            val hilfe = Hilfe(
                uri1,
                resources.getString(R.string.txt_titel_home_echzeitmessung),
                getString(R.string.em_txt_hilfe_messung)
            )
            val hilfe2 = Hilfe(
                uri2,
                resources.getString(R.string.txt_wlan_anmeldung),
                getString(R.string.txt_hilfe_text_anmeldung)
            )
            val hilfe3 = Hilfe(
                uri3,
                resources.getString(R.string.em_txt_hilfe_titel_berechtigung),
                resources.getString(R.string.em_txt_hilfe_berechtigung)
            )

            binding.title.text = getString(R.string.txt_titel_home_echzeitmessung)
            binding.beschreibung.text = getString(R.string.txt_beschreibung_echzeitmessung)

            hilfeListe = listOf(hilfe3, hilfe2, hilfe)


        }
        if (args.kontext == DIALOG_KONTEXT.HOME) {

            val uri1 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.em_messung)
            val uri2 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung_anlegen)
            val uri3 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messpunkt_erstellen)
            val uri4 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.visualisierung)

            val hilfe1 = Hilfe(
                uri1,
                resources.getString(R.string.txt_titel_home_echzeitmessung),
                getString(R.string.txt_beschreibung_echzeitmessung)
            )
            val hilfe2 = Hilfe(
                uri2,
                resources.getString(R.string.txt_titel_messung_hinzufuegen_de),
                getString(R.string.txt_hilfe_messung_hinzufuegen)
            )
            val hilfe3 = Hilfe(
                uri3,
                resources.getString(R.string.txt_messung_bearbeiten),
                getString(R.string.txt_hilfe_messung_verwalten)
            )
            val hilfe4 = Hilfe(
                uri4,
                resources.getString(R.string.txt_titel_home_visualisierung),
                getString(R.string.txt_beschreibung_visualisierung)
            )

            binding.title.text = getString(R.string.txt_hilfe_home_titel)
            binding.beschreibung.text = getString(R.string.txt_home_beschreibung)

            hilfeListe = listOf(hilfe1, hilfe2, hilfe3, hilfe4)
        }


        if (args.kontext == DIALOG_KONTEXT.MESSUNG_VERWALTEN) {

            val uri1 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung_verwalten)

            val hilfe = Hilfe(uri1, resources.getString(R.string.title_messungen_verwalten), "")

            binding.title.text = getString(R.string.txt_verwalte_deine_messungen)
            binding.beschreibung.text = getString(R.string.txt_hilfe_messung_verwalten)

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.MESSUNG_BEARBEITEN) {

            val uri1 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messpunkt_erstellen)
            val uri2 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messpunkt_bearbeiten)
            val uri3 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messungsnamen_aendern)

            val hilfe1 = Hilfe(
                uri1,
                resources.getString(R.string.txt_messpunkt_hinzufuegen),
                getString(R.string.txt_hilfe_messpunkt_erstellen)
            )
            val hilfe2 = Hilfe(
                uri2,
                resources.getString(R.string.txt_messpunkt_bearbeiten),
                getString(R.string.txt_hilfe_messpunkt_bearbeiten)
            )
            val hilfe3 = Hilfe(
                uri3,
                resources.getString(R.string.messungsname_aendern),
                getString(R.string.txt_hilfe_messungsnamen_aendern)
            )

            binding.title.text = getString(R.string.txt_messung_bearbeiten)
            binding.beschreibung.text = getString(R.string.txt_messung_bearbeiten_beschreibung)

            hilfeListe = listOf(hilfe1, hilfe2, hilfe3)
        }

        if (args.kontext == DIALOG_KONTEXT.MESSUNG_HINZUFUEGEN) {

            val uri1 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messung_anlegen)

            val hilfe = Hilfe(
                uri1,
                resources.getString(R.string.txt_wlan_anmeldung),
                getString(R.string.txt_hilfe_messung_anlegen)
            )

            binding.title.text = getString(R.string.txt_messung_hinzufuegen)
            binding.beschreibung.text = getString(R.string.txt_hilfe_messung_hinzufuegen)

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.MESSUGSLISTE) {

            val uri1 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messungsliste)

            val hilfe = Hilfe(
                uri1,
                resources.getString(R.string.txt_hilfe_messungsliste_titel),
                getString(R.string.txt_hilfe_messungsliste_text)
            )

            binding.title.text = resources.getString(R.string.title_messungen_de)
            binding.beschreibung.text = ""

            binding.rvHilfe.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToBottom = binding.title.id
            }

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.MESSPUNKT_ERFASSEN) {

            val uri1 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.messpunkt_erfassen)
            val uri2 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.bild_aufnehmen)

            val hilfe1 = Hilfe(
                uri1,
                getString(R.string.txt_hilfe_messpunkt_erfassen_titel),
                resources.getString(R.string.txt_hilfe_messpunkt_erfassen)
            )
            val hilfe2 = Hilfe(
                uri2,
                getString(R.string.txt_hilfe_bild_einfuegen_titel),
                resources.getString(R.string.txt_hilfe_bild_einfuegen_text)
            )

            binding.title.text = getString(R.string.txt_messpunkt_hinzufuegen)
            binding.beschreibung.text = getString(R.string.txt_messpunkterfassen_beschreibung)

            hilfeListe = listOf(hilfe1, hilfe2)
        }

        if (args.kontext == DIALOG_KONTEXT.VISUALISIERUNG_GRID) {
            val uri1 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.visualisierung)

            val hilfe = Hilfe(
                uri1,
                getString(R.string.txt_titel_home_visualisierung),
                getString(R.string.txt_beschreibung_visualisierung)
            )

            binding.title.text = ""
            binding.beschreibung.text = ""

            binding.rvHilfe.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToTop = binding.title.id
            }

            hilfeListe = listOf(hilfe)
        }

        if (args.kontext == DIALOG_KONTEXT.VISUALISIERUNG_DETAIL) {
            val uri1 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.visualisierung)

            val hilfe = Hilfe(
                uri1,
                getString(R.string.txt_titel_home_visualisierung),
                getString(R.string.txt_beschreibung_visualisierung)
            )

            binding.title.text = ""
            binding.beschreibung.text = ""

            binding.rvHilfe.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToTop = binding.title.id
            }

            hilfeListe = listOf(hilfe)
        }
        if (args.kontext == DIALOG_KONTEXT.VISULISIERUNG_FULLSCREEN_BILD) {

            val uri1 =
                Uri.parse("android.resource://" + requireActivity().packageName + "/" + R.raw.visualisierung)

            val hilfe = Hilfe(
                uri1,
                getString(R.string.txt_titel_home_visualisierung),
                getString(R.string.txt_beschreibung_visualisierung)
            )

            binding.title.text = ""
            binding.beschreibung.text = ""

            binding.rvHilfe.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToBottom = binding.title.id
            }

            hilfeListe = listOf(hilfe)
        }


        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvHilfe.layoutManager = LinearLayoutManager(activity?.applicationContext)
        val adapter = HilfeAdapter(hilfeListe, this.requireContext(), this)
        binding?.rvHilfe?.adapter = adapter
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onItemClick(position: Int) {

    }

}