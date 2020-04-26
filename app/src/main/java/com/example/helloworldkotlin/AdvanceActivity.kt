package com.example.helloworldkotlin

import kotlinx.android.synthetic.main.advance_layout.*

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdvanceActivity : AppCompatActivity() {
    private var Counter_ = 0
    private var characterData = CharacterGenerator.generate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advance_layout)
        initUI()
    }

    fun Counter(view: View) {
        Counter_++
        initUI()
    }

    fun Reset(view: View) {
        Counter_ = 0
        initUI()
    }

    private fun initUI() {
        //        editText.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
//                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
//                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
//                "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
//                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
//                "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.".toString()
    }
}