package com.example.helloworldkotlin

import kotlinx.android.synthetic.main.activity_main.*

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.launch

private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

private var Bundle.characterData
    get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
    set(value) = putSerializable(CHARACTER_DATA_KEY, value)

class MainActivity : AppCompatActivity() {
    private var characterData = CharacterGenerator.generate()
    private var myVarsData = CharacterGenerator.getMyDatas()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*characterData = savedInstanceState?.let{
            it.getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
        }?: CharacterGenerator.generate()*/
        characterData = savedInstanceState?.characterData ?: CharacterGenerator.generate()

        generateButton.setOnClickListener {
//            characterData = CharacterGenerator.generate()
//            myVarsData = CharacterGenerator.getMyDatas()
//            characterData = CharacterGenerator.fromApiData("halfling,Lars Kizzy,14,13,8")
            launch(UI) {
                characterData = fetchCharacterData().await()
                displayCharacterData()
            }
        }
        displayCharacterData()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
//        outState.putSerializable(CHARACTER_DATA_KEY,characterData)
        outState.characterData = characterData
    }

    private fun displayCharacterData() {
        characterData.run {
            nameTextView.text = name
            raceTextView.text = race
            dexterityTextView.text = dex
            wisdomTextView.text = wis
            strengthTextView.text = str
        }
        myVarsData.run {
            myvar1textView.text = myVar1
            myvar2textView.text = myVar2
            myvar3textView.text = myVar3

        }

    }
}
