package com.example.helloworldkotlin

import kotlinx.android.synthetic.main.counter_layout.*

import android.os.Bundle
import android.os.PersistableBundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private val TAG = "CounterActivity"
private val textOutstate = "edit_text"

class Counter_Activity : AppCompatActivity() {
    private var editText: EditText? = null
    private var btn: Button? = null
    private var txtView: TextView? = null
    private var numTimesClicked = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.counter_layout)
        editText = findViewById<EditText>(R.id.editTextName)
        btn = findViewById<Button>(R.id.btn_F)
        txtView = findViewById<TextView>(R.id.textViewName)
        txtView?.text = ""
        txtView?.movementMethod = ScrollingMovementMethod()
        btn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var textedit = editText?.text
                numTimesClicked += 1
                txtView?.append("$textedit clciked $numTimesClicked time\n")
                editText?.setText("")
            }
        })
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "On restart")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "On start")
    }

    fun Reset(view: View) {
    }

    private fun initUI() {

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textViewName?.text = savedInstanceState.getString(textOutstate)
        Log.d(TAG, "onRestoreInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState?.putString(textOutstate, textViewName?.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }
}