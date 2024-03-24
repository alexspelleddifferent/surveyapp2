package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private val countsViewModel: CountsViewModel by lazy {
        ViewModelProvider(this).get(CountsViewModel::class.java)
    }

    lateinit var buttonLeft: Button
    lateinit var buttonRight: Button
    lateinit var buttonResults: Button

    private val surveyResultsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> handleSurveyResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonLeft = findViewById(R.id.button_left)
        buttonRight = findViewById(R.id.button_right)
        buttonResults = findViewById(R.id.results_button)

        buttonLeft.setOnClickListener {
            countsViewModel.updateLeft()
        }

        buttonRight.setOnClickListener {
            countsViewModel.updateRight()
        }

        buttonResults.setOnClickListener {
            goToResults()
        }

    }

    private fun goToResults() {
        // creates intent to open new activity and saves current counts to the intent in order to pass on the data
        val resultsIntent = Intent (this, SurveyResultsActivity::class.java)
        resultsIntent.putExtra("counts_left", countsViewModel.getLeftCount() )
        resultsIntent.putExtra("counts_right", countsViewModel.getRightCount() )
        surveyResultsLauncher.launch(resultsIntent)
    }

    private fun handleSurveyResult (result: ActivityResult) {
        // processes the end of the results activity. if everything went fine get the data about counts
        // passed from the results result. otherwise if they clicked back give the user a message that
        // clicked back and that we are holding onto the past counts data
        val intent = result.data
        if (result.resultCode == RESULT_OK) {
            countsViewModel.setLeftCount(intent!!.getIntExtra("counts_left", 0))
            countsViewModel.setRightCount(intent!!.getIntExtra("counts_right", 0))
            Toast.makeText(this, "Carrying on...", Toast.LENGTH_SHORT).show()
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Returning to main menu. Saving prior results just in case", Toast.LENGTH_LONG).show()
        }
    }
}