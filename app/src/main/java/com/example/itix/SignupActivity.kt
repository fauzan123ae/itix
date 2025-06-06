package com.example.itix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var textSignIn: TextView
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        db = AppDatabase.getDatabase(this)

        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        textSignIn = findViewById(R.id.textSignIn)

        buttonSignUp.setOnClickListener {
            val username = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                // Check if email already exists
                val existingUser = db.userDao().getUserByEmail(email)
                withContext(Dispatchers.Main) {
                    if (existingUser != null) {
                        Toast.makeText(this@SignupActivity, "Email already registered", Toast.LENGTH_SHORT).show()
                        return@withContext
                    }

                    // Create new user
                    val newUser = User(
                        username = username,
                        email = email,
                        password = password
                    )

                    // Insert user into database
                    db.userDao().insertUser(newUser)

                    // Get the newly created user to get their ID
                    val user = db.userDao().getUserByEmail(email)
                    if (user != null) {
                        // Pass user data through Intent
                        val intent = Intent(this@SignupActivity, LoginActivity::class.java).apply {
                            putExtra("user_id", user.id)
                            putExtra("username", user.username)
                            putExtra("email", user.email)
                        }
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        textSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
} 