package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import com.example.snphmsapo.model.Number.formatNumber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var content: String = ""
    private var calculation: String = ""
    private var a: String = ""
    private var b: String = ""
    private var result: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickBtn()
    }

    private fun clickBtn() {
        binding.apply {
            btnMain0.setOnClickListener { appendNumber("0") }
            btnMain1.setOnClickListener { appendNumber("1") }
            btnMain2.setOnClickListener { appendNumber("2") }
            btnMain3.setOnClickListener { appendNumber("3") }
            btnMain4.setOnClickListener { appendNumber("4") }
            btnMain5.setOnClickListener { appendNumber("5") }
            btnMain6.setOnClickListener { appendNumber("6") }
            btnMain7.setOnClickListener { appendNumber("7") }
            btnMain8.setOnClickListener { appendNumber("8") }
            btnMain9.setOnClickListener { appendNumber("9") }
            btnMainDecimal.setOnClickListener { appendDecimal(".") }

            btnMainAdd.setOnClickListener {
                if (content != "") {
                    appendCalculation("+")
                }
            }
            btnMainSub.setOnClickListener {
                if (content != "") {
                    appendCalculation("-")
                }
            }
            imbtnMainMul.setOnClickListener {
                if (content != "") {
                    appendCalculation("x")
                }
            }
            imbtnMainDiv.setOnClickListener {
                if (content != "") {
                    appendCalculation("/")
                }
            }

            btnMainClean.setOnClickListener {
                clean()
            }
            btnMainPercent.setOnClickListener { }
            imbtnMainDelete.setOnClickListener {
                if (content != "" && txtMainResult.text.toString().isEmpty()) {
                    content = content.substring(0, content.length - 1)
                    updateLayout()
                }
            }

            btnMainResult.setOnClickListener {
                if (content.isNotEmpty() && b.isNotEmpty() && txtMainResult.text.toString()
                        .isEmpty()
                ) {
                    if ("+-x/".contains(calculation)) {
                        operate(calculation, a.toDouble(), b.toDouble())
                    }
                }
            }


        }
    }

    private fun clean() {
        content = ""
        a = ""
        b = ""
        calculation = ""
        binding.txtMainContent.text = "0"
        binding.txtMainResult.text = ""
    }

    private fun appendNumber(number: String) {
        if (binding.txtMainResult.text.toString().isNotEmpty()) {
            clean()
        }
        val containsOperator =
            content.contains("+") || content.contains("-") || content.contains("x") || content.contains(
                "/"
            )

        if (containsOperator) {
            b += number
            content = a + calculation + b
            updateLayout()
        } else {
            // Chuỗi không chứa toán tử
            a += number
            content = a
            updateLayout()
        }

    }

    private fun appendDecimal(decimal: String) {
        if (content.isEmpty()) {
            content = "0."
        }
        val containsOperator =
            content.contains("+") || content.contains("-") || content.contains("x") || content.contains(
                "/"
            )

        when {
            containsOperator && !b.contains(".") -> {
                b += decimal
            }

            !a.contains(".") -> {
                a += decimal
            }
        }

        content = a + calculation + b
        updateLayout()
    }


    private fun appendCalculation(s: String) {

        if (binding.txtMainResult.text.toString().isEmpty()) {
            val containsOperator =
                content.contains("+") || content.contains("-") || content.contains("x") || content.contains(
                    "/"
                )
            when {
                containsOperator && content.lastOrNull()?.let { "+-x/".contains(it) } == true -> {
                    // Thay thế phần tử cuối cùng bằng calcutation
                    content = content.dropLast(1) + s
                }

                !containsOperator -> {
                    content += s
                }
            }
            calculation = s
            updateLayout()
        }

    }

    private fun updateLayout() {
        binding.txtMainContent.text = content
    }

    private fun operate(operator: String, num1: Double, num2: Double) {
        when (operator) {
            "+" -> result = num1 + num2
            "-" -> result = num1 - num2
            "x" -> result = num1 * num2
            "/" -> {
                if (num2 != 0.0) {
                    result = num1 / num2
                } else {
                    result = Double.NaN
                    Toast.makeText(this, "Không thể chia cho số 0", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            else -> return
        }
        binding.txtMainResult.text = result.formatNumber()
    }


}