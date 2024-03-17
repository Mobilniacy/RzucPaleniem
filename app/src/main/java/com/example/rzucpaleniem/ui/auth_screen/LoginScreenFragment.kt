package com.example.rzucpaleniem.ui.auth_screen

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import apacheFileHelper
import com.example.rzucpaleniem.R
import com.example.rzucpaleniem.databinding.FragmentLoginScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout

/* todo: Zaimplementować */

class LoginScreenFragment : Fragment() {
    private var _binding: FragmentLoginScreenBinding? = null

    //TODO: zamienić to na firebase
    private lateinit var aph: apacheFileHelper  // Dla odczytu pliku csv - prymitywna metoda zastępcza dla firebase

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginScreenViewModel =
            ViewModelProvider(this)[RegisterScreenViewModel::class.java]

        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Todo: zamienić to na firebase
        aph = apacheFileHelper()

        // PO CO TO JEST?
        val textView: TextView = binding.textView6
        loginScreenViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val confirmTextView = view.findViewById<TextView>(R.id.textViewConfirm)
        confirmTextView?.setOnClickListener() {


            //Autentykacja logowania z plikiem csv

            if(!emptyUname() and !emptyPswd()){
                val uname = view.findViewById<TextInputLayout>(R.id.outlinedTextFieldUnameLogin).editText?.text.toString()
                val pswd = view.findViewById<TextInputLayout>(R.id.outlinedTextFieldPasswordLogin).editText?.text.toString()
                if(checkDB(uname,pswd)){
                    requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_authScreen_to_titleScreen)
                }
            }



            /*TODO: ogarnąć sprawdzanie warunków dla każdego pola logowania i w razie braku lub nieprawidłowości zaznaczyć odpowienie pole
            *  /ustawić fokus na dany element który się nie zgadza oraz wyświetlić komunikat w formie Toast.makeText albo na stałe w okienku pod
            *  nieprawidłowym czymś VVV*/

            //Jeżeli wszystkie warunki spełnione i porozumienie z Firebase zostało ustanowione i potwierdzone
            //Przenieść użytkownika do aplikacji
//            requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_authScreen_to_titleScreen)
//            Toast.makeText(context, "Text kliknięty", Toast.LENGTH_LONG).show()
        }

        //TODO: Dodać listenery dla przycisków przypominania hasła/loginu
    }

    //Funkcje pomocnicze dla logowania z csv
    private fun emptyUname(verbose: Boolean = true): Boolean {
        //Verbose - tryb gadatliwy - toast na ekranie dobre dla informowania, ale niedobre dla
        //sprawdzania w tle

        if (view?.findViewById<TextInputLayout>(R.id.outlinedTextFieldUnameLogin)?.editText?.text.toString()
                .isNotEmpty()){
            return false
        } else {
            if(verbose){
            Toast.makeText(context, "Pole login puste", Toast.LENGTH_LONG).show()
            }
            return true
        }
    }

    private fun emptyPswd(verbose: Boolean = true): Boolean {
        //Verbose - tryb gadatliwy - toast na ekranie dobre dla informowania, ale niedobre dla
        //sprawdzania w tle
        if (view?.findViewById<TextInputLayout>(R.id.outlinedTextFieldPasswordLogin)?.editText?.text.toString()
                .isNotEmpty()){
            return false
        } else {
            if(verbose){
                Toast.makeText(context, "Pole hasło puste", Toast.LENGTH_LONG).show()
            }
            return true
        }

    }
    private fun checkDB(uname: String, pswd: String, verbose: Boolean = true): Boolean{
        val znaleziono: Int = aph.findUserByUsernameAndPassword(requireActivity(),uname, pswd)
        Log.d(TAG, "checkDBint: $znaleziono")

        when (znaleziono) {
            0,1 -> {
                if(verbose) {
                    Toast.makeText(context, "Błędny login lub hasło", Toast.LENGTH_LONG).show()
                }
                return false
            }
            2 -> {
                if(verbose) {
                    Toast.makeText(context, "Dane poprawne", Toast.LENGTH_LONG).show()
                }
                return true
            }
        }
        return false
    }

}
