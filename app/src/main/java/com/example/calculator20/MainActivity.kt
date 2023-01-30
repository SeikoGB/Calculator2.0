package com.example.calculator20


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var one: Button
    private lateinit var two: Button
    private lateinit var three: Button
    private lateinit var four: Button
    private lateinit var five: Button
    private lateinit var six: Button
    private lateinit var seven: Button
    private lateinit var eight: Button
    private lateinit var nine: Button
    private lateinit var zero: Button

    private lateinit var point: Button
    private lateinit var clear: Button

    private lateinit var div: Button
    private lateinit var multiply: Button
    private lateinit var plus: Button
    private lateinit var minus: Button
    private lateinit var backscape:Button
    private lateinit var change_plus_minus:Button
    private lateinit var equal:Button


    private lateinit var problem: TextView
    private lateinit var result: TextView

    private var isPoint = true
    private var isSimvol = false
    private var isChanges = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
        one.setOnClickListener(this)
        two.setOnClickListener(this)
        three.setOnClickListener(this)
        four.setOnClickListener(this)
        five.setOnClickListener(this)
        six.setOnClickListener(this)
        seven.setOnClickListener(this)
        eight.setOnClickListener(this)
        nine.setOnClickListener(this)
        zero.setOnClickListener(this)


        clear.setOnClickListener {
            problem.text = "0"
            result.text = ""
            isPoint = true
            isSimvol = true
        }
        equal.setOnClickListener {
            problem.text=result.text.toString()
            isPoint=true
            isSimvol=true
            result.text=""
        }
        backscape.setOnClickListener {
            if (problem.text.length==1){
                problem.text = "0"
                result.text = "0"
        }
            else{
                problem.text = problem.text.dropLast(1).toString()
                result.text = calculate()
            }
        }

        point.setOnClickListener {
            if (isPoint) {
                problem.text = problem.text.toString() + "."
                isPoint = false
            }
        }
        change_plus_minus.setOnClickListener {
           isChanges=true
            calculate()
        }
        div.setOnClickListener {
            addSimvol("/")
        }
        multiply.setOnClickListener {
            addSimvol("*")
        }
        plus.setOnClickListener {
            addSimvol("+")
        }
        minus.setOnClickListener {
            addSimvol("-")
        }


    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        val btn = findViewById<Button>(view!!.id)
        if (problem.text == "0") {
            problem.text = ""
        }
        problem.text = problem.text.toString() + btn.text
        isSimvol = true
        result.text = calculate()
    }

    private fun calculate(): String {
        var list = createArray(problem.text.toString())
        if (isChanges){
            change(list)
        }
        div_multiply(list)
        plus_minus(list)

        return list.joinToString()
    }


    private fun createArray(s: String): MutableList<Any> {
        var list = mutableListOf<Any>()
        var temp = ""
        for (i in s) {
            if (i.isDigit() || i == '.') {
                temp += i
            } else {
                list.add(temp.toFloat())
                temp = ""
                list.add(i)
            }
        }
        if (temp.isNotEmpty()) {
            list.add(temp.toFloat())
        }
        return list
    }

    fun div_multiply(l: MutableList<Any>): MutableList<Any> {
        var list = l
        var i = 0
        while (list.contains('/') || list.contains('*')) {

            if (list[i] == '*' || list[i] == '/') {
                var prev = list[i - 1] as Float
                var next = list[i + 1] as Float
                var function = list[i]
                var res = 0f
                when (function) {
                    '/' -> {
                        res = prev / next
                    }
                    '*' -> {
                        res = prev * next
                    }
                }
                list.set(i-1,res)
                list.removeAt(i)
                list.removeAt(i)
                i = i-2
            }
            i++
        }
        return l
    }
        private fun plus_minus(l:MutableList<Any>): MutableList<Any> {
            var list = l
            var i =0
            while (list.contains('+') || list.contains('-')){

                if (list[i]=='+' || list[i]=='-'){
                var prev =list[i-1] as Float
                var next = list[i+1] as Float
                var function = list[i]
                var res = 0f
                when(function){
                    '+'->{
                        res = prev + next
                    }
                    '-'->{
                        res = prev - next
                    }
                }
                list.set(i-1,res)
                list.removeAt(i)
                list.removeAt(i)
                i = i-2
                }
                i++
            }
            return list
        }

    @SuppressLint("SetTextI18n")
    private fun addSimvol(simvol: String) {
        if (isSimvol) {
            problem.text = problem.text.toString() + simvol
            isSimvol = false
            isPoint = true
        } else {
            problem.text = problem.text.dropLast(1).toString()
        }

    }
    private fun change(l:MutableList<Any>):MutableList<Any>{
        var list = l
            if (list[list.size - 2] =='+'){
            list[list.size  - 2]='-'
        }
        else if (list[list.size-2]=='-'){
            list[list.size-2]='+'
        }
        return list
    }

    fun initUI() {
        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        six = findViewById(R.id.six)
        seven = findViewById(R.id.seven)
        eight = findViewById(R.id.eight)
        nine = findViewById(R.id.nine)
        zero = findViewById(R.id.zero)

        backscape=findViewById(R.id.bacscape)
        point = findViewById(R.id.dot)
        clear = findViewById(R.id.clear)
        div = findViewById(R.id.division)
        multiply = findViewById(R.id.multiolication)
        plus = findViewById(R.id.plus)
        minus = findViewById(R.id.minus)
        change_plus_minus = findViewById(R.id.plus_minus)
        equal=findViewById(R.id.equal)


        problem = findViewById(R.id.problem1)
        result = findViewById(R.id.result)
    }
}
