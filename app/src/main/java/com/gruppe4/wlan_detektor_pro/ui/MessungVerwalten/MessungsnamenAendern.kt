package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.MessungsnamenAendernFragmentBinding

/**
 * Messungsnamen ändern View
 * @author Klaus Buderer
 * @since 1.0.0
 */
class MessungsnamenAendern : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = MessungsnamenAendern()
    }

    private lateinit var viewModel: MessungsnamenAendernViewModel
    val args: MessungsnamenAendernArgs by navArgs()
    var _binding: MessungsnamenAendernFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MessungsnamenAendernFragmentBinding.inflate(layoutInflater)
        val root: View = binding.root

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessungsnamenAendernViewModel::class.java)

        binding.tvAktuellerName.text = args.messungsname
        var eingabeFeld = binding.etNeuerName
        var speicherButton = binding.speicher

        eingabeFeld.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Prüfung ob der Name bereits vergeben ist
                viewModel.namenValidierenRoutine(eingabeFeld.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //Prüfung ob der Name bereits vergeben ist oder keine Eingabe vorhanden ist
        viewModel.nameValide.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            //Disablen des Speicherbuttons bei ungueltiger Eingabe
            speicherButton.isEnabled = it && (eingabeFeld.text.toString().isNotEmpty())
            if (!it) {
                eingabeFeld.error = resources.getString(R.string.txt_namen_bereits_vergeben)
            }
        })

        binding.speicher.setOnClickListener {

            //Speichern des neuen Namens in die Datenbank
            viewModel.namenUpdate(args.messungsname, binding.etNeuerName.text.toString())

            //Rückkehr in das Messungsbearbeitungsbild mit übergabe des neuen Namens
            val action =
                MessungsnamenAendernDirections.actionMessungsnamenAendernToMessungBearbeitenFragment(
                    binding.etNeuerName.text.toString()
                )

            parentFragment?.let { it1 ->
                Navigation.findNavController(it1.requireView()).navigate(action)
            }
        }
    }
}

