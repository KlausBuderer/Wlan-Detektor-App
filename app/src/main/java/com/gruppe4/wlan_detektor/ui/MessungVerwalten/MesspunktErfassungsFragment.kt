package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.content.res.ColorStateList
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.MesspunktErfassungsFragmentBinding
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.model.Netzwerk.NetzwerkInfo
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException


class MesspunktErfassungsFragment : Fragment() {



    private var _binding: MesspunktErfassungsFragmentBinding? = null
    private val binding get() = _binding!!
    val args: MesspunktErfassungsFragmentArgs by navArgs()
    private var gebaeudeNamen: String = ""
    private var stockwerk: Int = -1
    private var raumname: String = ""
    lateinit var editGebaeude: EditText
    lateinit var editStockwerk: AutoCompleteTextView
    lateinit var editRaumname: EditText
    lateinit var editZusatzInfo: EditText
    lateinit var messungStarten: Button
    lateinit var abbrechen: Button
    lateinit var speichern: Button
    lateinit var progressBar: ProgressBar
    lateinit var signalText: TextView
    lateinit var messungsName: TextView
    private var stockwerkPosition: Int = -1
    private var signalStaerke: Int = 0
    private var messpunkt: TblMesspunkt = TblMesspunkt()

    companion object {
        fun newInstance() = MesspunktErfassungsFragment()
    }

    private lateinit var viewModel: MesspunktErfassungsViewModel

    override fun onResume() {
        super.onResume()
        val raeume = resources.getStringArray(R.array.stockwerk_array)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.dropdown_item, raeume)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MesspunktErfassungsFragmentBinding.inflate(inflater,container,false)

        editGebaeude = binding.etGebaeudeEdit
        editRaumname = binding.etRaumnameEdit
        editStockwerk = binding.autoCompleteTextView
        editZusatzInfo = binding.etZusatzinformationEdit
        speichern = binding.messpunktSpeichern
        abbrechen = binding.abbrechen
        messungStarten = binding.btnStartMesspunktMessung
        progressBar = binding.pgProgressBar
        signalText = binding.tvSignalstaerkeWert
        messungsName = binding.tvMessungNamen

        messungsName.text = args.messungsname



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MesspunktErfassungsViewModel::class.java)
        viewModel.messungsId = args.messungsId

        //Falls dieses Bild aus einer besteheder Messung aufgerufen wird ist der Speicherbutton
                //freigeschalten
                if (args.messpunktId != -1L){
                    viewModel.konditionStockwerk = true
                    viewModel.konditionRaumname = true
                    viewModel.konditionGebaeude = true

                    speichern.isEnabled = true
                }

        if (args.messpunktId != -1L){
            lifecycleScope.launch { viewModel.getMesspunkt(args.messpunktId) }
        }
        var netzwerkInfo = NetzwerkInfo(requireActivity().application)

        viewModel.messpunkt.observe(viewLifecycleOwner, Observer {
            try{
                messpunkt = it


            editGebaeude.editableText.insert(0,messpunkt.gebaeude)
            editRaumname.editableText.insert(0,messpunkt.raumname)
            editStockwerk.setText(editStockwerk.adapter.getItem(messpunkt.stockwerkID).toString(),false)
            editZusatzInfo.editableText.insert(0, messpunkt.zusatzinformation)
            progressBar.progress = messpunkt.pegelmessung
            progressBar.progressTintList = ColorStateList.valueOf(netzwerkInfo.progressBarFarbeEinstellen(messpunkt.pegelmessung))
            signalText.text = messpunkt.pegelmessung.toString()
            }catch (e: Exception){

            }
        })


        Toast.makeText(
            requireContext(),
            args.messpunktId.toString() + args.messungsId.toString() + args.messungsname,Toast.LENGTH_LONG
        ).show()

        binding.btnStartMesspunktMessung.setOnClickListener{
            lifecycleScope.launch { viewModel.startUpdates()}
        }

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            progressBar.progressTintList = ColorStateList.valueOf(it)
        })

        viewModel.signalstaerke.observe(viewLifecycleOwner, Observer {
           progressBar.progress = it
            signalText.text = it.toString()
            signalStaerke = it
        })

        editGebaeude.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editGebaeude.text.isNotEmpty()){
                    viewModel.konditionGebaeude = true
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (editGebaeude.text.isNotEmpty()){
                    viewModel.konditionGebaeude = true
                }

                speichern.isEnabled = viewModel.buttonFreigeben()
            }

        })

        editRaumname.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if (editRaumname.text.isNotEmpty()){
                   viewModel.konditionRaumname = true
               }
            }

            override fun afterTextChanged(s: Editable?) {
                if (editRaumname.text.isNotEmpty()){
                    viewModel.konditionRaumname = true
                }

                speichern.isEnabled = viewModel.buttonFreigeben()
            }

        })

        editStockwerk.setOnItemClickListener{parent, view, position, id ->
            stockwerkPosition = position
            viewModel.konditionStockwerk = true
            speichern.isEnabled = viewModel.buttonFreigeben()
        }

        speichern.setOnClickListener{


            if (args.messpunktId == -1L) {
                var _messpunkt: TblMesspunkt = TblMesspunkt(
                    args.messungsId,
                    editGebaeude.text.toString(),
                    stockwerkPosition,
                    editRaumname.text.toString(),
                    editZusatzInfo.text.toString(),
                    signalStaerke,
                    "dB",
                    viewModel.datum,
                    viewModel.zeit
                )
                viewModel.messpunktSpeichern(_messpunkt)
                Log.e("Messpunkt erfassen viewmodel: ", "messpunkt id -1")
            }else{
                var _messpunkt: TblMesspunkt = TblMesspunkt(
                    messpunkt.idmesspunkt,
                    args.messungsId,
                    editGebaeude.text.toString(),
                    stockwerkPosition,
                    editRaumname.text.toString(),
                    editZusatzInfo.text.toString(),
                    signalStaerke,
                    "dB",
                    messpunkt.erfassungsDatum,
                    messpunkt.erfassungsZeit,
                    viewModel.datum,
                    viewModel.zeit
                )
                viewModel.messpunktUpdate(_messpunkt)
                Log.e("Messpunkt erfassen viewmodel: ", "messpunkt nicht id -1")
            }



            val action = MesspunktErfassungsFragmentDirections.actionMesspunktErfassungsFragmentToMessungBearbeitenFragment(
                args.messungsname
            )

            Navigation.findNavController(binding.root).navigate(action)
        }

    }

}