package com.example.shadowboxerapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

        private lateinit var comboTextView: TextView
        private lateinit var startButton: Button
        private lateinit var stopButton: Button
        private lateinit var textToSpeech: TextToSpeech

        private val combos = arrayOf(
                "Roll",
                "Catch",
                "Block",
                "Pull",
                "Slip",
                "Shoulder",
                "Top",
                "Body",
                "Left-Block",
                "Tripple",
                "Tripple-SLip",
                "Double Hook",
                "Base"
                // Add more combos as needed
        )

        private var isRunning = false

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                comboTextView = findViewById(R.id.comboTextView)
                startButton = findViewById(R.id.startButton)
                stopButton = findViewById(R.id.stopButton)
                textToSpeech = TextToSpeech(this, this)

                startButton.setOnClickListener {
                        if (!isRunning) {
                                startRandomComboCallout()
                                isRunning = true
                                startButton.isEnabled = false
                                stopButton.isEnabled = true
                        }
                }

                stopButton.setOnClickListener {
                        if (isRunning) {
                                stopRandomComboCallout()
                                isRunning = false
                                startButton.isEnabled = true
                                stopButton.isEnabled = false
                        }
                }
        }

        override fun onDestroy() {
                super.onDestroy()
                // Release the TextToSpeech resources
                textToSpeech.stop()
                textToSpeech.shutdown()
        }

        override fun onInit(status: Int) {
                if (status == TextToSpeech.SUCCESS) {
                        // Set the language for Text-to-Speech (you can use Locale.US or any other supported locale)
                        val result = textToSpeech.setLanguage(Locale.US)

                        // Check if the Text-to-Speech is supported in the current device and language
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                // Handle the case when Text-to-Speech is not available or not supported in the selected language
                        }
                } else {
                        // Handle the case when Text-to-Speech initialization failed
                }
        }

        private fun startRandomComboCallout() {
                val handler = Handler(Looper.getMainLooper())

                val runnable = object : Runnable {
                        override fun run() {
                                if (isRunning) {
                                        val randomCombo = getRandomCombo()
                                        comboTextView.text = randomCombo
                                        textToSpeech.speak(randomCombo, TextToSpeech.QUEUE_FLUSH, null, null)
                                        handler.postDelayed(this, 1300)
                                }
                        }
                }

                handler.post(runnable)
        }

        private fun getRandomCombo(): String {
                val randomIndex = (0 until combos.size).random()
                return combos[randomIndex]
        }

        private fun stopRandomComboCallout() {
        }
}



