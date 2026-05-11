package com.example.a2firstapp

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

//so this lists store all the images for the foods and tools
    // index 0 is the default or empty state
    private val foodImages = listOf(
        R.drawable.download__4__removebg_preview,
        R.drawable.salmon_img, R.drawable.rice_img, R.drawable.bread_img, R.drawable.potato_img, R.drawable.strawberry_img
    )

    private val toolImages = listOf(
        R.drawable.download__4__removebg_preview,
        R.drawable.oven_img, R.drawable.ricecooker_img, R.drawable.toaster_img, R.drawable.fryer_img, R.drawable.blender_img
    )

// this is basically a 2D list or grid where each food and tool combo gives a result image
    private val mealImages = listOf(
        listOf(R.drawable.baked_salmon, R.drawable.fish_soup, R.drawable.burnt_salmon, R.drawable.fried_salmon, R.drawable.salmon_mush),
        listOf(R.drawable.dry_rice, R.drawable.perfect_rice, R.drawable.grilled_rice, R.drawable.greasy_rice, R.drawable.rice_paste),
        listOf(R.drawable.hard_bread, R.drawable.soggy_bread, R.drawable.toast, R.drawable.greasy_bread, R.drawable.baby_food),
        listOf(R.drawable.dry_potato, R.drawable.mushy_potato, R.drawable.burnt_potato, R.drawable.fries, R.drawable.potato_sludge),
        listOf(R.drawable.soggy_berry, R.drawable.cooked_berry, R.drawable.burnt_berry, R.drawable.fried_berry, R.drawable.smoothie)
    )
//these track which option the user is currently on
    private var foodIndex = 0
    private var toolIndex = 0

    //checking if the user has actually selected both
    private var foodSelected = false
    private var toolSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

// this ones are just basically linking the buttons and views from xml file
        val btnFood = findViewById<ImageButton>(R.id.imageButton3)
        val btnTool = findViewById<ImageButton>(R.id.imageButton4)
        val plateTrigger = findViewById<ImageView>(R.id.imageView)
        val btnReset = findViewById<ImageButton>(R.id.btnReset)
        val btnInfo = findViewById<ImageButton>(R.id.btnInfo)

//so when user taps food button, it cycles through food images
        btnFood.setOnClickListener {
            foodIndex = if (foodIndex >= 5) 1 else foodIndex + 1
            btnFood.setImageResource(foodImages[foodIndex])
            foodSelected = true
        }

// same thing for tool button
        btnTool.setOnClickListener {
            toolIndex = if (toolIndex >= 5) 1 else toolIndex + 1
            btnTool.setImageResource(toolImages[toolIndex])
            toolSelected = true
        }

//when plate is tapped, it only shows result if both are selected
        plateTrigger.setOnClickListener {
            if (foodSelected && toolSelected) {
                showResultPopup()
            }
        }

//reset button basically brings everything back to its original state which is just being empty
        btnReset.setOnClickListener {
            foodIndex = 0
            toolIndex = 0
            foodSelected = false
            toolSelected = false
            btnFood.setImageResource(foodImages[0])
            btnTool.setImageResource(toolImages[0])
            plateTrigger.setImageResource(R.drawable.plate_removebg_preview)
        }

// shows instructions popup
        btnInfo.setOnClickListener {
            showInstructions()
        }
    }

    private fun showInstructions() { //creates a dialog or popup
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.modal_result)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val titleTxt = dialog.findViewById<TextView>(R.id.modalTitle)
        val msgTxt = dialog.findViewById<TextView>(R.id.modalCritique)

        titleTxt.text = "HOW TO PLAY"
        titleTxt.setTextColor(Color.parseColor("#D2B48C")) // Earth tone tan
        msgTxt.text = "1. Tap the 2 top buttons to choose the food and tools.\n2. Tap the plate to see Gordon's comments.\n3. Try to find all 5 perfect dishes!"

        dialog.show()
    }

    private fun showResultPopup() { //adjusting index because list starts at 0 but selections start from 1
        val f = foodIndex - 1
        val t = toolIndex - 1
        val isSuccess = (f == t) // success only if food matches the tool

        //updates the plate image based on combo
        val mainPlate = findViewById<ImageView>(R.id.imageView)
        mainPlate.setImageResource(mealImages[f][t])

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.modal_result)

        //customizing popup position and size
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(0f)
            setGravity(Gravity.BOTTOM)
            val params = attributes
            params.y = 150
            attributes = params
        }

        val titleTxt = dialog.findViewById<TextView>(R.id.modalTitle)
        val msgTxt = dialog.findViewById<TextView>(R.id.modalCritique)

        //changes title depending if it's a success or fail
        titleTxt.text = if (isSuccess) "STUNNING!" else "YOU DONKEY!"
        titleTxt.setTextColor(if (isSuccess) Color.parseColor("#556B2F") else Color.parseColor("#A0522D"))

        //all Gordon Ramsey's responses for each combo
        val resultsGrid = listOf(
            listOf("Perfectly baked! Flaky, moist, absolutely stunning!", "What is this?! Fish soup gone wrong!", "You’ve turned expensive fish into shoe leather!", "Greasy and overcooked! Disgraceful!", "What is this mush?! You’ve completely destroyed it!"),
            listOf("Dry, uneven, and embarrassing! Who cooks rice like that?!", "Fluffy, light, and cooked to perfection!", "Grilled rice?! That’s a disaster!", "Soggy, greasy mess! Absolutely ridiculous!", "You’ve made rice paste?! What on earth!"),
            listOf("Hard, dry, and completely useless!", "Soggy and sad! That’s not how you make bread!", "Golden brown and crisp! Finally, something done right!", "Greasy, heavy and pointless! Why would you do that?!", "You’ve turned bread into baby food!"),
            listOf("Dry and boring! Where’s the flavor?!", "Mushy and dull! No texture at all!", "Burnt outside, raw inside — useless!", "Crispy, golden perfection! That’s what I’m talking about!", "You’ve turned potatoes into sludge!"),
            listOf("Soggy, dull, and completely wrong!", "You’ve cooked the life out of them!", "Burnt and sticky! Absolutely dreadful!", "Fried strawberries?! That’s a greasy nightmare!", "Smooth, fresh, and vibrant! Beautifully done!")
        )

        //picks the correct critique of Gordon Ramsey based on combo
        msgTxt.text = resultsGrid[f][t]
        dialog.show()
    }
}