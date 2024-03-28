package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // class variables
    private lateinit var dbHelper: UserDatabaseHelper
    private lateinit var binding: ActivityMainBinding // View binding instance
    private lateinit var sharedPreferences: SharedPreferences // Shared preference variable

    // Activity life cycle creation of activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize lateinit values
        dbHelper = UserDatabaseHelper(this)
        sharedPreferences = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)

        // Check if the user is already logged in
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            // User is already logged in, redirect to MathsQuiz activity
            startActivity(Intent(this, MathsQuiz::class.java))
            finish() // Finish MainActivity to prevent returning back on logout
        }
        // Action to take when clicks on sign up text view, if he is new to application
        binding.textViewSignUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish() // Finish MainActivity
        }
        // Action to take on Login Button when user clicks
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            if (dbHelper.checkUser(email, password)) {
                // Login successful, save login status in shared preferences
                with(sharedPreferences.edit()) {
                    putBoolean("isLoggedIn", true)
                    apply()
                }
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MathsQuiz::class.java))
                finish() // Finish MainActivity after successful login
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
