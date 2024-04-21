package com.mobilniacy.rzucpaleniem.ui.auth_screen

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
import androidx.navigation.findNavController
import apacheFileHelper
import com.mobilniacy.rzucpaleniem.R
import com.mobilniacy.rzucpaleniem.databinding.FragmentLoginScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import com.mobilniacy.rzucpaleniem.MainActivity

/* todo: Zaimplementować */

class LoginScreenFragment : Fragment() {
    private var _binding: FragmentLoginScreenBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val loginScreenViewModel =
//            ViewModelProvider(this)[RegisterScreenViewModel::class.java]

        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // PO CO TO JEST?
//        val textView: TextView = binding.textView6
//        loginScreenViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
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


            //Autentykacja logowania

            val auth = (activity as MainActivity).auth
            if(!emptyMail() and !emptyPswd()){
                val password = view.findViewById<TextInputLayout>(R.id.outlinedTextFieldPasswordLogin).editText?.text.toString()
                val email = view.findViewById<TextInputLayout>(R.id.outlinedTextFieldMailLogin).editText?.text.toString()
//                if(checkDB(uname,pswd)){
//                    requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_authScreen_to_titleScreen)
//                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((activity as MainActivity)) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            goToTitle()

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                context,
                                task.exception?.message ?: "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()

                            Toast.makeText(
                                context,
                                "mail:"+email+" haslo:"+password,
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
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


    private fun goToTitle(){
        if (checkIfSignedIn()){
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigate(R.id.action_loginScreen_to_titleScreen)
        } else {
            Toast.makeText(
                context,
                "Wystąpił problem z sesją, powrót do okna wyboru",
                Toast.LENGTH_SHORT,
            ).show()
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                .navigate(R.id.action_loginScreen_to_authScreen)
        }
    }

    private fun checkIfSignedIn(): Boolean {
        if ((activity as MainActivity).auth.currentUser != null) {
            return true
        }else{
            return false
        }
    }



    //Funkcje pomocnicze dla logowania z csv
    private fun emptyMail(verbose: Boolean = true): Boolean {
        //Verbose - tryb gadatliwy - toast na ekranie dobre dla informowania, ale niedobre dla
        //sprawdzania w tle

        if (view?.findViewById<TextInputLayout>(R.id.outlinedTextFieldMailLogin)?.editText?.text.toString()
                .isNotEmpty()){
            return false
        } else {
            if(verbose){
                Toast.makeText(context, "Pole mail puste", Toast.LENGTH_LONG).show()
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

}
