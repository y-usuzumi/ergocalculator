package org.ratina.ergocalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private var textExpr : TextView? = null
    private var model : ErgoCalculatorViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = ViewModelProvider(this)[ErgoCalculatorViewModel::class.java]
        textExpr = findViewById(R.id.textExpr)
        model?.getExpr()?.observe(this, Observer { expr ->
            textExpr?.text = expr.toString()
        })
        val buttonsLayout : ViewGroup = findViewById(R.id.layoutControls)
        buttonsLayout.children.filter { it is Button }.forEach {
            (it as Button).setOnClickListener {
                val btn : Button = it as Button
                model?.sendKeystroke(btn.tag as String)
            }
        }
    }
}
