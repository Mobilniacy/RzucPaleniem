package com.mobilniacy.rzucpaleniem

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mobilniacy.rzucpaleniem.databinding.ActivityMainBinding

// ***Jak bedzie firebase
//import com.example.rzucpaleniem.AuthHelper

import android.view.View
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics //firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    private lateinit var firebaseAnalytics: FirebaseAnalytics //firebase analityka? nie działa

    lateinit var auth: FirebaseAuth //firebase autoryzacja

    lateinit var initialFragmentId: String

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)


        //***Jak bedzie firebase
        //var authHelper: AuthHelper

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*TODO: Ustawić start destination na LoadingScreen w mobile_navigation i updatować go
           w miare przebieguinicjalizacji wszystkich elementów. Myślę że to dobry pomysł.
           Ewentualnie symulacja we fragmencie ładowania :)*/

        //TODO: Jak będzie firebase to użyć authHelpera, może się na coś zda.
        // Sprawdzenie, czy użytkownik jest zalogowany


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_stats, R.id.navigation_title_screen, R.id.navigation_settings
            )
        )
//         V Włączony actionBar u góry ekranu, aby wyłączyć odkomentować importa i linijke poniżej
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        // Log a custom event
//        val bundle = Bundle().apply {
//            putString(FirebaseAnalytics.Param.ITEM_NAME, "example_item")
//        }
//        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)
//
//        // Set a user property
//        firebaseAnalytics.setUserProperty("favorite_screen", "stats_screen")
    }

    override fun onStart() {
        super.onStart()

        //Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore
    }

//    public override fun onStart() {
//        super.onStart()
//        // Sprawdzamy czy user jest zalogowany i ustawiamy odpowiednie okienko dla menu nawigacji.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            val initialFragmentId = R.id.navigation_title_screen
//        } else {
//            val initialFragmentId = R.id.navigation_login
//        }
//    }


    fun showBars(){
        // Pokazanie dolnego van bar
        val navView = this.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.visibility = View.VISIBLE

        // Dla paska narzędzi (Toolbar)
        val actionBar = (this as AppCompatActivity).supportActionBar
        actionBar?.show()
    }

}

