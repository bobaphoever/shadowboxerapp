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

        data class ComboWithDelay(val combo: String, val delayMillis: Long)

        private val combos = listOf(
                ComboWithDelay("Roll", 1300),
                ComboWithDelay("Catch", 1300),
                ComboWithDelay("Block", 1300),
                ComboWithDelay("Pull", 1300),
                ComboWithDelay("Slip", 1300),
                ComboWithDelay("Shoulder", 1300),
                ComboWithDelay("Top", 1000),
                ComboWithDelay("Body", 1500),
                ComboWithDelay("Left-Block", 1300),
                ComboWithDelay("Tripple", 1600),
                ComboWithDelay("Slip-3", 1700),
                ComboWithDelay("Double-Hook", 1200),
                ComboWithDelay("Base", 1500),
                ComboWithDelay("Slip-2", 1400),
                ComboWithDelay("Roll-2", 1500),
                ComboWithDelay("1 2 Roll", 1600),
                ComboWithDelay("Touch", 2200),
                ComboWithDelay("Hook", 2200),
                ComboWithDelay("3-Cross", 2500),
                ComboWithDelay("Liver", 2200)

                // Add more combos with specific delays as needed
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
                                        val comboWithDelay = getRandomCombo()
                                        comboTextView.text = comboWithDelay.combo
                                        textToSpeech.speak(comboWithDelay.combo, TextToSpeech.QUEUE_FLUSH, null, null)
                                        handler.postDelayed(this, comboWithDelay.delayMillis)
                                }
                        }
                }

                handler.post(runnable)
        }

        private fun getRandomCombo(): ComboWithDelay {
                val randomIndex = (0 until combos.size).random()
                return combos[randomIndex]
        }

        private fun stopRandomComboCallout() {
        }
}



