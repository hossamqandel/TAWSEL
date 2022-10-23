package com.hossam.tawsel.feature_main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hossam.tawsel.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}