package com.sevenminuteworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_exercise.*

//TODO(Step 1 - Add an ExerciseActivity.)-->
class ExerciseActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restExerciseTimer : CountDownTimer? = null
    private var restExerciseProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        //TODO(Step 5 - Setting up the action bar using the toolbar and adding a back arrow button to it.)-->
        //START
        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Us the backpressed (close the activity) functionality when clicking the toolbar back button
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        llExerciseView.visibility = View.GONE
        setupRestView()

        //END
    }

    override fun onDestroy() {
        if(restTimer!=null){
            restProgress=0
            restTimer!!.cancel()
            super.onDestroy()

        }
        if(restExerciseTimer!=null){
            restExerciseProgress=0
            restExerciseTimer!!.cancel()
        }
    }


    private fun restProgressBar(){
        progressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10-restProgress
                tvTimer.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"You will know start the exercise",Toast.LENGTH_SHORT).show()
                llRestView.visibility = View.GONE
                llExerciseView.visibility=View.VISIBLE
                setupExerciseRestView()

            }
        }.start()

    }
    private fun setupRestView(){
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        restProgressBar()
    }
    private fun setupExerciseRestView(){
        if(restExerciseTimer!=null){
            restExerciseTimer!!.cancel()
            restExerciseProgress=0
        }
        restExerciseProgressBar()
    }
    private fun restExerciseProgressBar(){
        progressExerciseBar.progress = restExerciseProgress
        restExerciseTimer = object : CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restExerciseProgress++
                progressExerciseBar.progress = 30-restExerciseProgress
                tvExerciseTimer.text = (30-restExerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"You have finished this exercise",Toast.LENGTH_SHORT).show()
            }
        }.start()

    }
}