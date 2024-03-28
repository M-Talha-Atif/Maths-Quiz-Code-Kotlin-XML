package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp.databinding.ActivitySignupBinding
import kotlinx.coroutines.delay

class SignUp: AppCompatActivity() {
    private lateinit var dbHelper: UserDatabaseHelper // Database Helper instance
    private lateinit var binding: ActivitySignupBinding // Binding Instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = UserDatabaseHelper(this)

        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Finish SignUpActivity
        }

        // Action to take when clicks on sign up button
        binding.buttonSignUp.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            if(email!="" && password!="") {

                when (dbHelper.addUser(email, password)) {
                    // Signup successful
                    "success" -> {
                        Toast.makeText(
                            this,
                            "Successful SignUp. Now login please!!",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }

                    "email" -> {
                        Toast.makeText(
                            this,
                            "Invalid Email. Please try again!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    "failure" -> {
                        Toast.makeText(
                            this,
                            "Failure. Please try again!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            }
            else {
                Toast.makeText(
                    this,
                    "Fill out all details. Please try again!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}