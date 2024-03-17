package com.example.rzucpaleniem.ui.auth_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.rzucpaleniem.R
import com.example.rzucpaleniem.databinding.FragmentLoginScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/* todo: Zaimplementować */

class LoginScreenFragment : Fragment() {
    private var _binding: FragmentLoginScreenBinding? = null



    private val binding get() = _binding!!

    lateinit var btnDatePicker: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loginScreenViewModel =
            ViewModelProvider(this)[RegisterScreenViewModel::class.java]

        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // PO CO TO JEST?
        val textView: TextView = binding.textView6
        loginScreenViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Ukrycie dolnego van bar
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

            /*TODO: ogarnąć sprawdzanie warunków dla każdego pola logowania i w razie braku lub nieprawidłowości zaznaczyć odpowienie pole
            *  /ustawić fokus na dany element który się nie zgadza oraz wyświetlić komunikat w formie Toast.makeText albo na stałe w okienku pod
            *  nieprawidłowym czymś VVV*/

            //Jeżeli wszystkie warunki spełnione i porozumienie z Firebase zostało ustanowione i potwierdzone
            //Przenieść użytkownika do aplikacji
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_registerScreen_to_titleScreen)
            Toast.makeText(context, "Text kliknięty", Toast.LENGTH_LONG).show()
        }

        //TODO: Dodać listenery dla przycisków przypominania hasła/loginu
    }
}
