package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.FragmentMessungHinzufuegenBinding
import com.gruppe4.wlan_detektor.ui.Echtzeitmessung.EchtzeitmessungViewModel

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


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val raeume = resources.getStringArray(R.array.raeumlichkeiten_array)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, raeume)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       /* viewModel =
            ViewModelProvider(this)[MessungHinzufuegenViewModel::class.java]*/

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
                viewModel.konditionNamenValide = viewModel.namenValidieren(eingabeNamen.text.toString())
            }

            //Signalisierung das Namen der Messung bereits vergeben ist
            override fun afterTextChanged(s: Editable?) {
                if (!viewModel.namenValidieren(eingabeNamen.text.toString())) {
                    eingabeNamen.backgroundTintList = ColorStateList.valueOf(Color.RED)
                } else {
                    eingabeNamen.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                }

            }

        })

        netzwahl.setOnClickListener{
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        //Pruefen Netzwerknamen für Ausgabe
                if (viewModel.pruefenNetzAnmeldung()){
                    netzNamen.text = viewModel.netzwerkInfo()
                }


        //Pruefen ob Art der Raeumlichkeit gewählt wurde
        raeumlichkeit.setOnItemClickListener{ parent, view, position, id ->
            viewModel.pruefenRaeumlichkeitWahl(position)
            Toast.makeText(requireContext(),position.toString(), Toast.LENGTH_LONG).show()
            speichernButton.isEnabled = viewModel.buttonFreigabe()
        }

        Toast.makeText(requireContext(),viewModel.konditionRaum.toString() + viewModel.konditionNamenValide.toString() + viewModel.konditionNetzAngemeldet.toString(), Toast.LENGTH_LONG).show()


        //Speicherbutton Freigabe
        viewModel.speicherFreigabe.observe(viewLifecycleOwner, Observer {
            speichernButton.isEnabled =  it})

        speichernButton.setOnClickListener{
            viewModel.messungSpeichern()

            //Navigation in Messung Bearbeiten Bild
            Navigation.findNavController(it).navigate(
                R.id.action_navigation_messung_to_messungHinzufuegen)
        }

    }




}