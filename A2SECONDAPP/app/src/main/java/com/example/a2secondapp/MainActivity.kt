package com.example.a2secondapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    //these start as empty strings so the app knows nothing is picked yet
    private var selectedBase = ""
    private var selectedHerb = ""
    private var selectedSweetener = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //linking the code to the actual buttons made in the xml file
        val textWelcome = findViewById<TextView>(R.id.textWelcome)
        val infoButton = findViewById<ImageButton>(R.id.infoButton)
        val profButton = findViewById<ImageButton>(R.id.profButton)
        val buttonInfuse = findViewById<Button>(R.id.buttonInfuse)

        //sharedpreferences is just a simple way to save the user's name on the device
        val sharedPref = getSharedPreferences("ApothecaryPrefs", Context.MODE_PRIVATE)
        textWelcome.text = "Welcome, ${sharedPref.getString("user_name", "Traveler")}"

        //this handles the profile popup where you can type in your name
        profButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val input = EditText(this)
            builder.setTitle("Apothecary Profile").setMessage("Enter your name:").setView(input)
                .setPositiveButton("Save") { _, _ ->
                    val newName = input.text.toString()
                    if (newName.isNotEmpty()) {
                        //saving the name so it stays there
                        sharedPref.edit().putString("user_name", newName).apply()
                        textWelcome.text = "Welcome, $newName"
                    }
                }.setNegativeButton("Cancel", null).show()
        }

        //just an instruction to show the user how to play the game
        infoButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Lab Guide")
                .setMessage("You must select ONE from each row:\n1. A Base\n2. An Herb\n3. A Sweetener (or 'None')\n4. Press 'Infuse Brew'")
                .setPositiveButton("OK", null).show()
        }


        //when a button is clicked, it updates the variables so the app remembers the choice
        findViewById<ImageButton>(R.id.imageButton4).setOnClickListener { selectedBase = "Water"; toast("Base selected") }
        findViewById<ImageButton>(R.id.imageButton5).setOnClickListener { selectedBase = "Green Tea"; toast("Base selected") }
        findViewById<ImageButton>(R.id.imageButton6).setOnClickListener { selectedBase = "Oat Milk"; toast("Base selected") }
        findViewById<ImageButton>(R.id.imageButton7).setOnClickListener { selectedBase = "Vinegar"; toast("Base selected") }


        findViewById<ImageButton>(R.id.imageButton).setOnClickListener { selectedHerb = "Ginger"; toast("Herb selected") }
        findViewById<ImageButton>(R.id.imageButton2).setOnClickListener { selectedHerb = "Turmeric"; toast("Herb selected") }
        findViewById<ImageButton>(R.id.imageButton3).setOnClickListener { selectedHerb = "Mint"; toast("Herb selected") }
        findViewById<ImageButton>(R.id.imageButton8).setOnClickListener { selectedHerb = "Lavender"; toast("Herb selected") }


        findViewById<ImageButton>(R.id.imageButton9).setOnClickListener { selectedSweetener = "Honey"; toast("Sweetener selected") }
        findViewById<ImageButton>(R.id.imageButton10).setOnClickListener { selectedSweetener = "Maple"; toast("Sweetener selected") }
        findViewById<ImageButton>(R.id.imageButton11).setOnClickListener { selectedSweetener = "Stevia"; toast("Sweetener selected") }
        findViewById<ImageButton>(R.id.imageButton12).setOnClickListener { selectedSweetener = "None"; toast("No sweetener added") }

        //this is the logic that starts the brew process
        buttonInfuse.setOnClickListener {

            //i used || to check if any row is still empty as well as if even one thing is missing, it stops them right here
            if (selectedBase.isEmpty() || selectedHerb.isEmpty() || selectedSweetener.isEmpty()) {
                // If anything is missing, show an error and do NOT brew
                AlertDialog.Builder(this)
                    .setTitle("Incomplete Recipe")
                    .setMessage("The infusion requires one selection from every shelf to stabilize.")
                    .setPositiveButton("Try Again", null).show()
            } else {

                //an intent is how it switch from this screen to the result screen
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("HERB", selectedHerb)
                intent.putExtra("BASE", selectedBase)
                intent.putExtra("SWEET", selectedSweetener)
                startActivity(intent) //switches to result page
            }
        }
    }

    //shows the popup messages at the bottom
    private fun toast(m: String) = Toast.makeText(this, m, Toast.LENGTH_SHORT).show()
}