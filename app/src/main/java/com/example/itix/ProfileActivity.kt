package com.example.itix

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.itix.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvUsername.text = "zanzan"
        binding.tvEmail.text = "zanzan@gmail.com"

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
