package com.example.rzucpaleniem

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rzucpaleniem.databinding.ActivityMainBinding

import com.google.firebase.analytics.FirebaseAnalytics //firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAnalytics: FirebaseAnalytics //firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}