package com.example.a2secondapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result)

        //linking up the text and button from the xml file
        val textResultTitle = findViewById<TextView>(R.id.textResultTitle)
        val textResultInfo = findViewById<TextView>(R.id.textResultInfo)
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        //unpacking the somewhat like a luggage that was sent from the first screen
        //so we're grabbing the herb here, the based, and the sweetener names
        val herb = intent.getStringExtra("HERB")
        val base = intent.getStringExtra("BASE")
        val sweet = intent.getStringExtra("SWEET") ?: "None"

        //this creates a nice little sentence about the sweetener so it sounds nice in the results
        val sweetPhrase = if (sweet == "None") "pure and unsweetened" else "lightly sweetened with $sweet"

        //if for some reason the herb or base data didnt make it, show error message
        if (herb.isNullOrEmpty() || base.isNullOrEmpty()) {
            textResultTitle.text = "Invalid Output"
            textResultInfo.text = "The botanical sequence was incomplete. No brew could be formed."
        } else { //the "when" block is like a checklist. it looks at herb you chose and matches it to the right text
            when (herb) {
                "Ginger" -> {
                    textResultTitle.text = "The Ginger $base Brew"
                    textResultInfo.text = "A warming tonic for digestion, $sweetPhrase."
                }
                "Turmeric" -> {
                    textResultTitle.text = "The Turmeric $base Brew"
                    textResultInfo.text = "Supports immunity and heart health, $sweetPhrase."
                }
                "Mint" -> {
                    textResultTitle.text = "The Mint $base Brew"
                    textResultInfo.text = "Soothes the stomach and clears the mind, $sweetPhrase."
                }
                "Lavender" -> {
                    textResultTitle.text = "The Lavender $base Brew"
                    textResultInfo.text = "Promotes deep sleep and lowers stress, $sweetPhrase."
                }
                else -> { // this is the backup. if someone gets in a weird herb name, it shows this
                    textResultTitle.text = "Invalid Output"
                    textResultInfo.text = "This herb is unknown to the apothecary."
                }
            }
        }
        //takes you back to the home screen
        buttonBack.setOnClickListener { finish() }
    }
}