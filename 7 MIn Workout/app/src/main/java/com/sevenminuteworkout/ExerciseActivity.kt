package com.sevenminuteworkout

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_exercise.*

//TODO(Step 1 - Add an ExerciseActivity.)-->
class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restExerciseTimer : CountDownTimer? = null
    private var restExerciseProgress = 0
    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var player: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        tts = TextToSpeech(this,this)
        //TODO(Step 5 - Setting up the action bar using the toolbar and adding a back arrow button to it.)-->
        //START
        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Us the backpressed (close the activity) functionality when clicking the toolbar back button
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseList = Constants.defaultExerciseList()
        setupRestView()

        //END
    }

    override fun onDestroy() {
        if(tts!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                tts!!.stop()
                tts!!.shutdown()

            }
        }
        if(restTimer!=null){
            restProgress=0
            restTimer!!.cancel()


        }
        if(restExerciseTimer!=null){
            restExerciseProgress=0
            restExerciseTimer!!.cancel()

        }
        super.onDestroy()
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
                currentExercisePosition++

                setupExerciseRestView()

            }
        }.start()

    }
    private fun setupRestView(){
        player = MediaPlayer.create(applicationContext,R.raw.press_start)
        llExerciseView.visibility=View.GONE
        llRestView.visibility=View.VISIBLE
        player!!.start()
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        tvUpcomingExercise.text = exerciseList!![currentExercisePosition+1].getName()
        speakOut(get_ready_for.text.toString())
        speakOut(upcoming_Exercise.text.toString())
        speakOut(exerciseList!![currentExercisePosition+1].getName())
        restProgressBar()
    }
    private fun setupExerciseRestView(){

        if(restExerciseTimer!=null){
            restExerciseTimer!!.cancel()
            restExerciseProgress=0
        }
        restExerciseProgressBar()
        tv_image.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()

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
                if(currentExercisePosition< exerciseList!!.size - 1){
                        setupRestView()
                }
                else{
                    Toast.makeText(this@ExerciseActivity,"You have finished the 7 Minute Workout",Toast.LENGTH_SHORT).show()
                }

            }
        }.start()

    }

    override fun onInit(status: Int) {

    }
    private fun speakOut(text: String){
        tts!!.speak(text,TextToSpeech.QUEUE_ADD,null,"")
    }
}