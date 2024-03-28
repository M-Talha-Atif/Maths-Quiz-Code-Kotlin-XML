package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityResultBinding


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding // View binding instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve score from intent extras
        val score = intent.getIntExtra("SCORE", 0)

        // Set text based on score range
        binding.textViewResult.text = when (score) {
            in 8..10 -> "Congratulations! Your score is $score"
            in 5..7 -> "Good! Your score is $score"
            in 0..4 -> "Oh unfortunately, your score is $score"
            else -> "Invalid score" // Handle any other cases
        }

        // Set click listener for the Restart Button
        binding.buttonRestart.setOnClickListener {
            // Navigate back to the MathsQuiz activity
            startActivity(Intent(this, MathsQuiz::class.java))
            finish() // Close the current ResultActivity
        }
    }
}
