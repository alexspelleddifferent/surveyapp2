package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class SurveyResultsActivity : AppCompatActivity() {

    private val countsViewModel: CountsViewModel by lazy {
        ViewModelProvider(this).get(CountsViewModel::class.java)
    }

    lateinit var buttonReset: Button
    lateinit var countLeft: TextView
    lateinit var countRight: TextView
    lateinit var buttonContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_results)

        buttonReset = findViewById(R.id.reset_button)
        countLeft = findViewById(R.id.count_left)
        countRight = findViewById(R.id.count_right)
        buttonContinue = findViewById(R.id.continue_button)

        // get the counts from the passed intent, and update the data on screen to show those numbers
        getInitCounts()
        updateBoth()

        buttonReset.setOnClickListener {
            countsViewModel.reset()
            updateBoth()
        }

        buttonContinue.setOnClickListener {
            // when button is pressed we pack up the counts, which is needed given we have the option
            // to reset here, as part of the result intent, then finish this activity
            val returnIntent = Intent()
            returnIntent.putExtra("counts_left", countsViewModel.getLeftCount() )
            returnIntent.putExtra("counts_right", countsViewModel.getRightCount() )
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }

    fun updateBoth() {
        countLeft.text = getString(R.string.left_count, countsViewModel.getLeftCount())
        countRight.text = getString(R.string.right_count, countsViewModel.getRightCount())
    }

    fun getInitCounts() {
        // function takes in data carried in from the intent from previous activity and updates
        // the counts for a new countsviewmodel.
        countsViewModel.setLeftCount(intent.getIntExtra("counts_left",0))
        countsViewModel.setRightCount(intent.getIntExtra("counts_right",0))
    }
}