package com.example.rzucpaleniem.ui.auth_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rzucpaleniem.R
import com.example.rzucpaleniem.databinding.FragmentRegisterScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

//import com.example.rzucpaleniem.AuthHelper

class RegisterScreenFragment : Fragment() {

    private var _binding: FragmentRegisterScreenBinding? = null



    private val binding get() = _binding!!

    lateinit var btnDatePicker: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val registerScreenViewModel =
            ViewModelProvider(this).get(RegisterScreenViewModel::class.java)

        _binding = FragmentRegisterScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // PO CO TO JEST?
        val textView: TextView = binding.textView2
        registerScreenViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnDatePicker = requireView().findViewById(R.id.floatingActionButton)

        // when floating action button is clicked
        btnDatePicker.setOnClickListener {
            // Initiate date picker with
            // MaterialDatePicker.Builder.datePicker()
            // and build it using build()
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(requireActivity().supportFragmentManager, "DatePicker")

            // Setting up the event for when ok is clicked
            datePicker.addOnPositiveButtonClickListener {
                // formatting date in dd-mm-yyyy format.
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
                Toast.makeText(context, "$date is selected", Toast.LENGTH_LONG).show()

                val outlinedTextBirthDay =
                    activity?.findViewById<TextInputLayout>(R.id.outlinedTextFieldBirthDate)

                outlinedTextBirthDay?.editText?.setText(dateFormatter.format(Date(it)))
            }

            // Setting up the event for when cancelled is clicked
            datePicker.addOnNegativeButtonClickListener {
                Toast.makeText(
                    context,
                    "${datePicker.headerText} is cancelled",
                    Toast.LENGTH_LONG
                ).show()
            }

            // Setting up the event for when back button is pressed
            datePicker.addOnCancelListener {
                Toast.makeText(context, "Date Picker Cancelled", Toast.LENGTH_LONG).show()
            }
        }
        // Disabling manual input in TextInputEditText
        val outlinedTextBirthDay =
            activity?.findViewById<TextInputLayout>(R.id.outlinedTextFieldBirthDate)

        outlinedTextBirthDay?.editText?.setOnClickListener {
            // Show date picker dialog when TextInputEditText is clicked
            btnDatePicker.performClick()
//            Toast.makeText(context, "Klik klik", Toast.LENGTH_LONG).show()
        }

        // ClickListener dla przycisku confirm dla rejestracji
        // Korzysta z akcji zdefiniowanej w navigation mobile
        val confirmTextView = view.findViewById<TextView>(R.id.textViewConfirm)
        confirmTextView?.setOnClickListener() {

            /*TODO: ogarnąć sprawdzanie warunków dla każdego pola rejestracji i w razie braku lub nieprawidłowości zaznaczyć odpowienie pole
            *  /ustawić fokus na dany element który się nie zgadza oraz wyświetlić komunikat w formie Toast.makeText albo na stałe w okienku pod
            *  nieprawidłowym czymś VVV*/

            //Jeżeli wszystkie warunki spełnione i porozumienie z Firebase zostało ustanowione i potwierdzone
            //Przenieść użytkownika do aplikacji
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_registerScreen_to_titleScreen)
            Toast.makeText(context, "Text kliknięty", Toast.LENGTH_LONG).show()
        }

        //TODO: Dodać listenery dla przycisków dotyczących prywatności oraz TOS
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Ukrycie dolnego nav bar
        val navView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.visibility = View.GONE

        // Dla paska narzędzi (Toolbar)
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.hide()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}