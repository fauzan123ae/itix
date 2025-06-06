package com.example.itix

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
        tvUsername = findViewById(R.id.tvUsername)
        tvEmail = findViewById(R.id.tvEmail)
        btnBack = findViewById(R.id.btnBack)

        // Get user data from Intent
        val username = intent.getStringExtra("username") ?: ""
        val email = intent.getStringExtra("email") ?: ""

        // Set user data to TextViews
        tvUsername.text = username
        tvEmail.text = email

        // Set up back button
        btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}
