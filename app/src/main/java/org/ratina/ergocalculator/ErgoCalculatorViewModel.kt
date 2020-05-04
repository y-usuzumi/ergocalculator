package org.ratina.ergocalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class EvaluateResult {
    data class Success(val result: String) : EvaluateResult()
    data class PartialSuccess(val result: String, val message: String) : EvaluateResult()
    data class Error(val message: String) : EvaluateResult()
}

class ErgoCalculatorViewModel : ViewModel() {
    private val expr = MutableLiveData<String>()
    private val lastResult = MutableLiveData<EvaluateResult>()

    init {
        expr.value = ""
    }

    fun getExpr(): LiveData<String> = expr

    fun sendKeystroke(keystroke: String) {
        when (keystroke) {
            "clear" -> {
                lastResult.value = null
                expr.value = ""
            }
            "del" -> {
                if (lastResult.value != null) {
                    lastResult.value = null
                    expr.value = ""
                } else if (expr.value!!.isNotEmpty()) {
                    expr.value = expr.value!!.substring(0 until expr.value!!.length - 1)
                }
            }
            "add" -> {
                if (lastResult.value != null) {
                    lastResult.value = null
                }
                expr.value += "+"
            }
            "sub" -> {
                if (lastResult.value != null) {
                    lastResult.value = null
                }
                expr.value += "-"
            }
            "mul" -> {
                if (lastResult.value != null) {
                    lastResult.value = null
                }
                expr.value += "*"
            }
            "div" -> {
                if (lastResult.value != null) {
                    lastResult.value = null
                }
                expr.value += "/"
            }
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "." -> if (lastResult.value != null) {
                lastResult.value = null
                expr.value = keystroke
            } else {
                expr.value += keystroke
            }
            "eql" -> {
                val result = evaluate()
                when (result) {
                    is EvaluateResult.Success -> expr.value = result.result
                    is EvaluateResult.PartialSuccess -> expr.value =
                        "${result.result} (message: ${result.message})"
                    is EvaluateResult.Error -> expr.value = "Error: ${result.message}"
                }
                lastResult.value = result
            }
        }
    }

    private fun evaluate(): EvaluateResult {
        return EvaluateResult.Success("OK")
    }
}