package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentMessungHinzufuegenBinding
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung


class MessungHinzufuegen : Fragment() {

    companion object {
        fun newInstance() = MessungHinzufuegen()
    }

    private lateinit var viewModel: MessungHinzufuegenViewModel
    private var _binding: FragmentMessungHinzufuegenBinding? = null
    lateinit var speichernButton: Button
    lateinit var eingabeNamen: EditText
    lateinit var netzNamen: TextView
    lateinit var raeumlichkeit: AutoCompleteTextView
    lateinit var netzwahl: Button
    var raeumlichkeitPosition: Int = -1


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val raeume = resources.getStringArray(R.array.raeumlichkeiten_array)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, raeume)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        viewModel.startUpdateCoroutine()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMessungHinzufuegenBinding.inflate(inflater, container, false)




        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessungHinzufuegenViewModel::class.java)

        speichernButton = binding.messungSpeichern
        eingabeNamen = binding.editTextMessungName
        netzNamen = binding.txtNetzwerkTitel
        raeumlichkeit = binding.autoCompleteTextView
        netzwahl = binding.netzwerkwahlMessung




        eingabeNamen.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            //Validierung des Namens der Messung
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.namenValidierenRoutine(eingabeNamen.text.toString())
                Log.e("OnChange","${eingabeNamen.text.toString()}")


            }

            //Signalisierung das Namen der Messung bereits vergeben ist
            override fun afterTextChanged(s: Editable?) {
                var validierung: Boolean = true

                viewModel.nameValide.observe(viewLifecycleOwner, Observer {
                    validierung = it
                })

                Log.e("validierung","${validierung}")

                if (viewModel.result > 0) {
                    eingabeNamen.error = resources.getString(R.string.txt_namen_bereits_vergeben)
                    viewModel.konditionNamenValide = false
                    speichernButton.isEnabled = viewModel.buttonFreigabe()
                } else {
                    eingabeNamen.error = null
                    viewModel.konditionNamenValide = true
                    speichernButton.isEnabled = viewModel.buttonFreigabe()
                }
            }
        })

        netzwahl.setOnClickListener {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        //Pruefen Netzwerknamen für Ausgabe
        if (viewModel.pruefenNetzAnmeldung()) {
            netzNamen.text = viewModel.netzwerkInfo()
        }
        viewModel.netzwerkInfo.observe(viewLifecycleOwner, Observer {

            if (it.ssid != "<unknown ssid>"){
                netzNamen.text = it.ssid
            }else{
                netzNamen.text = "Bitte mit Wlan verbinden"
            }
        })

        //Pruefen ob Art der Raeumlichkeit gewählt wurde
        raeumlichkeit.setOnItemClickListener { parent, view, position, id ->
            viewModel.pruefenRaeumlichkeitWahl(position)
            raeumlichkeitPosition = position
            speichernButton.isEnabled = viewModel.buttonFreigabe()
        }

        Toast.makeText(
            requireContext(),
            viewModel.konditionRaum.toString() + viewModel.konditionNamenValide.toString() + viewModel.konditionNetzAngemeldet.toString(),
            Toast.LENGTH_LONG
        ).show()


        //Speicherbutton Freigabe
        viewModel.speicherFreigabe.observe(viewLifecycleOwner, Observer {
            speichernButton.isEnabled = it
        })

        speichernButton.setOnClickListener {


            var messung: TblMessung = TblMessung(
                eingabeNamen.text.toString(),
                netzNamen.text.toString(),
                raeumlichkeitPosition,
                viewModel.getDatum(),
                viewModel.getZeit()
            )

            viewModel.messungSpeichern(messung)

            val action =
                MessungHinzufuegenDirections.actionMessungHinzufuegenToMessungBearbeitenFragment(
                    eingabeNamen.text.toString()
                )

            Navigation.findNavController(binding.root).navigate(action)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopUpdateCoroutine()
    }
}

