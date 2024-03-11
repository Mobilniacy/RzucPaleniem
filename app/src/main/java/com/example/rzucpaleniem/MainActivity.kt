package com.example.rzucpaleniem

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rzucpaleniem.databinding.ActivityMainBinding

// ***Jak bedzie firebase
//import com.example.rzucpaleniem.AuthHelper

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.core.content.edit
import com.google.firebase.analytics.FirebaseAnalytics //firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAnalytics: FirebaseAnalytics //firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //***Jak bedzie firebase
        //var authHelper: AuthHelper

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

//  ***Jak bedzie firebase
//        // Sprawdzenie, czy użytkownik jest zalogowany
//        val isUserLoggedIn = authHelper.isUserLoggedIn()
//        val isUserLoggedIn = true
//
//        // Wybór fragmentu na podstawie stanu zalogowania użytkownika
//        val initialFragmentId = if (isUserLoggedIn) {
//            R.id.navigation_login // Fragment po zalogowaniu
//        } else {
//            R.id.navigation_title_screen // Fragment logowania
//        }
        firebaseAnalytics = FirebaseAnalytics.getInstance(this) //firebase

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
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_NAME, "example_item")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)

        // Set a user property
        firebaseAnalytics.setUserProperty("favorite_screen", "stats_screen")
    }

    fun showBars(){
        // Pokazanie dolnego van bar
        val navView = this.findViewById<BottomNavigationView>(R.id.nav_view)
        navView?.visibility = View.VISIBLE

        // Dla paska narzędzi (Toolbar)
        val actionBar = (this as AppCompatActivity).supportActionBar
        actionBar?.show()
    }

}

