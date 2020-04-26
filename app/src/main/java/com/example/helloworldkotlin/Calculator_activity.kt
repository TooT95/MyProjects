package com.example.helloworldkotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.calculator_layout.*
import java.lang.NumberFormatException

class Calculator_activity : AppCompatActivity() {
    private var pendingOperation = ""
    public val key_Operation = "operation_key"
    public val key_result = "result_key"
    public val key_operand = "operand_key"
    public val key_pendingoperand = "pendingoperand_key"
    private var operand1: Double? = null
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator_layout)
        init_UI()
    }

    private fun init_UI() {
        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }
        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = op
            displayOperation.text = pendingOperation
        }
        btn_0.setOnClickListener(listener)
        btn_1.setOnClickListener(listener)
        btn_2.setOnClickListener(listener)
        btn_3.setOnClickListener(listener)
        btn_4.setOnClickListener(listener)
        btn_5.setOnClickListener(listener)
        btn_6.setOnClickListener(listener)
        btn_7.setOnClickListener(listener)
        btn_8.setOnClickListener(listener)
        btn_9.setOnClickListener(listener)
        btn_dot.setOnClickListener(listener)
        btn_equal.setOnClickListener(opListener)
        btn_divide.setOnClickListener(opListener)
        btn_minus.setOnClickListener(opListener)
        btn_plus.setOnClickListener(opListener)
        btn_multiple.setOnClickListener(opListener)
    }

    private fun performOperation(value: Double, op: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = op
            }
            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN
                } else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operation.text = savedInstanceState?.getString(key_Operation)
        result.setText(savedInstanceState?.getString(key_result))
        pendingOperation = savedInstanceState?.getString(key_pendingoperand).toString()
        operand1 = savedInstanceState.getDouble(key_operand)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(key_Operation, operation.text.toString())
        outState.putString(key_result, result.text.toString())
        outState.putString(key_pendingoperand, pendingOperation)
        operand1?.let { outState.putDouble(key_operand, it) }
    }
}