package com.example.expensetracker

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class MainActivity : AppCompatActivity() {
    private lateinit var tvTodayExpence: TextView
    private lateinit var btnExpenseAnalytics: Button
    private lateinit var etAmount: EditText
    private lateinit var spinCategory: Spinner
    private lateinit var spinPaymentType: Spinner
    private lateinit var dpDate: DatePicker
    private lateinit var btnAddExpense: Button
    private lateinit var db: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = DataBaseHelper(this)

        spinCategory = findViewById(R.id.categoryexpenceSPIN)
        spinPaymentType = findViewById(R.id.paymentmetSPIN)
        btnAddExpense = findViewById(R.id.addexpenseBTN)
        etAmount = findViewById(R.id.amountET)
        dpDate = findViewById(R.id.dateDP)

        val categoryOfExpense =
            listOf("Food", "Transport", "Shopping", "Bills", "Entertainment", "Medicine", "Other")
        val paymentTypes = listOf("Cash", "Card", "UPI", "Net Banking")

        val categoryAdapter = ArrayAdapter(this, R.layout.spinner_item, categoryOfExpense)
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinCategory.adapter = categoryAdapter

        val paymentAdapter = ArrayAdapter(this, R.layout.spinner_item, paymentTypes)
        paymentAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinPaymentType.adapter = paymentAdapter

        val defaultValueEx = "Food"
        val posEx = categoryOfExpense.indexOf(defaultValueEx)
        if (posEx >= 0) {
            spinCategory.setSelection(posEx)
        }

        val defaultValuePay = "Cash"
        val posPay = paymentTypes.indexOf(defaultValuePay)
        if (posPay >= 0) {
            spinPaymentType.setSelection(posPay)
        }


        tvTodayExpence = findViewById(R.id.todayexpenceTV)
        val todayexpence: Int = db.todayExpense()
        if (todayexpence != 0) {
            val tvTodayExpencestring = "₹$todayexpence"
            tvTodayExpencestring.toString()
            tvTodayExpence.setText(tvTodayExpencestring)
        }

    }

    override fun onResume() {
        super.onResume()

        btnExpenseAnalytics = findViewById(R.id.expenseanalysisBTN)
        btnExpenseAnalytics.setOnClickListener {
            val EHintent = Intent(this, Analytics::class.java)
            startActivity(EHintent)
        }

        btnAddExpense.setOnClickListener {
            val etAmountValue = etAmount.text.toString().toIntOrNull()
            val spinCategoryValue: String = spinCategory.selectedItem.toString()
            val spinPaymentTypeValue: String = spinPaymentType.selectedItem.toString()

            val year: Int = dpDate.year
            val month: Int = dpDate.month + 1
            val day: Int = dpDate.dayOfMonth
            intent.putExtra("year", year)
            intent.putExtra("month", month)
            intent.putExtra("day", day)

            if (etAmountValue != null && etAmountValue > 0) {
                val expencevaluedb = expensedbvar(
                    0,
                    etAmountValue,
                    spinCategoryValue,
                    spinPaymentTypeValue,
                    year,
                    month,
                    day
                )
                db.insertExpense(expencevaluedb)

                val updatedTodayExpense = db.todayExpense()
                tvTodayExpence.text = "₹$updatedTodayExpense"

                MotionToast.darkToast(
                    this,
                    "Added successfully",
                    "₹$etAmountValue has been recorded!",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    Typeface.DEFAULT
                )
                etAmount.text.clear()
            } else {
                MotionToast.darkToast(
                    this,
                    "Failed to Add Amount",
                    "Please enter a Amount greater than 0",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    Typeface.DEFAULT
                )
            }
        }

    }
}
