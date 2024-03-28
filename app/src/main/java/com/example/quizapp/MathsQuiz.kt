package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp.databinding.ActivityMathsQuizBinding

class MathsQuiz : AppCompatActivity() {
    // Class variables
    private lateinit var binding: ActivityMathsQuizBinding // View binding instance
    private var score = 0 // Variable to track score
    private var questionCount = 0 // Variable to track question count
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMathsQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)

        // Accessing views using view binding
        binding.textViewScore.text = "Score: $score" // Initial score

        // Generate and set initial equation
        setNextQuestion()

        // Set click listener for the submit button
        binding.buttonSubmit.setOnClickListener {
            submitGuess()
        }

        // Set click listener for the home button
        binding.buttonHome.setOnClickListener {
            with(sharedPreferences.edit()) {
                putBoolean("isLoggedIn", false)
                apply()
            }
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
            // Create Intent class object to navigate to Main Activity that is Login Screen
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // finish MathsQuiz Activity

        }
    }

    private fun setNextQuestion() {
        // Generate an equation with random numbers and operator
        val number1 = (0..10000).random()
        val number2 = (1..10000).random()
        val operator = listOf("+", "-", "*").random()
        val equation = "$number1 $operator $number2"
        binding.textViewEquation.text = equation
    }

    private fun submitGuess() {
        // Increment question count
        questionCount++

        // Get user's guess
        val userAnswer = binding.editTextAnswer.text.toString().toIntOrNull()

        // Calculate the correct answer
        val equationParts = binding.textViewEquation.text.split(" ")
        val number1 = equationParts[0].toInt()
        val operator = equationParts[1]
        val number2 = equationParts[2].toInt()
        val correctAnswer = calculateAnswer(number1, operator, number2)

        // Check if user's guess is correct
        if (userAnswer == correctAnswer) {
            // Increment score
            score++
            // Show toast for correct answer
            Toast.makeText(this, "Your answer $questionCount is correct", Toast.LENGTH_SHORT).show()
        } else {
            // Show toast for incorrect answer
            Toast.makeText(this, "Your answer $questionCount is incorrect", Toast.LENGTH_SHORT).show()
        }
        binding.editTextAnswer.text.clear()

        // Update score display
        binding.textViewScore.text = "Score: $score"

        // Check if it's time to move to ResultActivity
        if (questionCount == 10) {
           moveToResultActivity()
        } else {
            // Move to next question
            setNextQuestion()
        }
    }

    private fun calculateAnswer(number1: Int, operator: String, number2: Int): Int {
        return when (operator) {
            "+" -> number1 + number2
            "-" -> number1 - number2
            "*" -> number1 * number2
            "/" -> number1 / number2
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }
    private fun moveToResultActivity() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("SCORE", score)
        startActivity(intent)
    }

}