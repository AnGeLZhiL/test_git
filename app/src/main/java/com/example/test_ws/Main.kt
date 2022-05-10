package com.example.test_ws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val bnv = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)

        bnv.setupWithNavController(navController)
    }

    fun ButtonClick(view: View) {
        val navController = findNavController(R.id.fragmentContainerView)
        navController.navigate(R.id.action_secondFragment_to_thirdFragment)
    }


}