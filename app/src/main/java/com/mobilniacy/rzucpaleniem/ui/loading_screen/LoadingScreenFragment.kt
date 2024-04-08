package com.mobilniacy.rzucpaleniem.ui.loading_screen

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mobilniacy.rzucpaleniem.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobilniacy.rzucpaleniem.MainActivity
import com.mobilniacy.rzucpaleniem.databinding.FragmentLoadingScreenBinding
import kotlinx.coroutines.*


// TODO: zaimplementować
class LoadingScreenFragment : Fragment() {
    private var _binding: FragmentLoadingScreenBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        val loadingScreenViewModel =
//            ViewModelProvider(this)[LoadingScreenViewModel::class.java]

        _binding = FragmentLoadingScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root


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

        // Sprawdzamy czy user jest zalogowany i ustawiamy odpowiednie okienko dla menu nawigacji.






    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startLoading()
    }


    /*TODO: 1. Zaimplementować postęp paska ładowania. Sprzężyć go z aktualnym rozmiarem okna
    *  i szarym paskiem danym jako tło, żeby było ładnie i płynnie. */

    /*TODO: 2. Po załadowaniu przenieść użytkownika do aplikacji jeżeli był zalogowany, dane się zgadzają, a jego sesja nie wygasła.
    *  W przeciwnym razie wyświetlić ekran wyboru - logowanie/rejestracja */

    // Funkcja symulująca postęp ładowania
    private fun startLoading() {

        val txtView = requireView().findViewById<TextView>(R.id.textView4)

        // Uruchomienie nowej korutyny
        CoroutineScope(Dispatchers.Main).launch {

            txtView.text = "Inicjalizacja użytkownika"
            // Wprowadzenie opóźnienia na 3 sekundy
            delay(2000)

            // Tutaj możesz umieścić logikę dotyczącą przekierowania użytkownika po zakończeniu ładowania
            // Na przykład, jeśli użytkownik jest zalogowany, możesz przenieść go do aplikacji
            // w przeciwnym razie możesz wyświetlić ekran logowania/rejestracji
            val currentUser = (activity as MainActivity).auth.currentUser
            var transferDestination = 1 //Selection screen

            if (currentUser != null) {
                transferDestination = 0 //Title screen
                Toast.makeText(context, "Wykryto aktywną sesję", Toast.LENGTH_LONG).show()
            }
                // Wprowadzenie kolejnego opóźnienia na 2 sekundy
                delay(2000)

                // Aktualizacja widoku po zakończeniu ładowania
                txtView.text = "Zainicjalizowano"

                delay(1000)
                if (transferDestination == 1) {
                    txtView.text = "Przenoszę do okna autoryzacji"
                    delay(1000)
                    requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                        .navigate(R.id.action_loadingScreen_to_authScreen)
                } else {
                    txtView.text = "Przenoszę do aplikacji"
                    delay(1000)
                    requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                        .navigate(R.id.action_loadingScreen_to_titleScreen)
                }
            }
        }
}