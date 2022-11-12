package com.hossam.tawsel.feature_main.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.hossam.tawsel.R
import com.hossam.tawsel.core.SharedPref
import com.hossam.tawsel.feature_main.data.remote.ITawselService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()
        hideBottomWithSomeFragments()
    }

    private fun setupBottomNavigationView(){
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        bottomNavView.setupWithNavController(navController)
        NavigationUI.setupWithNavController(bottomNavView, navController)
    }

    private fun hideBottomWithSomeFragments(){
        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        navController.addOnDestinationChangedListener { _, destination, _ ->
        when(destination.id){
            R.id.splashFragment -> { findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE }
            R.id.loginFragment -> { findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE }
            R.id.resetPasswordFragment -> { findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE }
            R.id.notificationsFragment -> { findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE }
            else -> {
                findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.VISIBLE }
            }
        }
    }
}