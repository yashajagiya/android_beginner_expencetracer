package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aachartcreator.aa_toAAOptions
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

class Analytics : AppCompatActivity() {


    private lateinit var tvTotalExpences: TextView
    private lateinit var tvAverageDaily: TextView
    private lateinit var tvHighstCategoryType: TextView
    private lateinit var tvHighstCategoryAmount: TextView
    private lateinit var piePieChartCategory: AAChartView
    private lateinit var piePieChartPayment: AAChartView
    private lateinit var btnExpenseHistry: Button
    private lateinit var db: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_analytics)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = DataBaseHelper(this)

        // value for chart

        //val foodsum: Int = db.sumofcatgory("Food")
        // val transportsum: Int = db.sumofcatgory("Transport")
        //val shoppingsum: Int = db.sumofcatgory("Shopping")
        // val billssum: Int = db.sumofcatgory("Bills")
        // val entertainmentsum: Int = db.sumofcatgory("Entertainment")
        //  val medicinesum: Int = db.sumofcatgory("Medicine")
//val othersum: Int = db.sumofcatgory("Other")

        //val cashsum: Int = db.sumofpaymenttype("Cash")
        // val cardsum: Int = db.sumofpaymenttype("Card")
        // val upisum: Int = db.sumofpaymenttype("UPI")
        //  val netbankignsum: Int = db.sumofpaymenttype("Net Banking")


        //get date data

        val year: Int = intent.getIntExtra("year", 0)
        val month: Int = intent.getIntExtra("month", 0)
        val day: Int = intent.getIntExtra("day", 0)

        //cureent date /month / year

        val curenttoday = LocalDate.now()
        val currentyear = curenttoday.dayOfYear
        val currentmonth = curenttoday.dayOfMonth


        //total expence

        val totalexpence = db.sumofexpence()
        val totalexpencefloat: Float = totalexpence.toFloat()
        totalexpence.toString()
        tvTotalExpences = findViewById(R.id.totalexpencesTV)
        tvTotalExpences.setText("₹$totalexpence")

        //average

        tvAverageDaily = findViewById(R.id.averagedailyTV)
        if ((currentyear / 4 == 0 && currentyear % 100 != 0) || (currentyear % 400 == 0)) {
            val totaleexpenceaverage: Float = totalexpencefloat / 366
            val totaleexpenceaverage2f = "%.2f".format(totaleexpenceaverage)
            totaleexpenceaverage2f.toString()
            tvAverageDaily.setText("₹$totaleexpenceaverage2f (366 Days)")
        } else {
            val totaleexpenceaverage: Float = totalexpencefloat / 365
            val totaleexpenceaverage2f = "%.2f".format(totaleexpenceaverage)
            totaleexpenceaverage2f.toString()
            tvAverageDaily.setText("₹$totaleexpenceaverage2f (365 Days)")
        }

        // high category
        tvHighstCategoryType = findViewById(R.id.highstcategorytypeTV)
        tvHighstCategoryAmount = findViewById(R.id.highstcategoryamountTV)
        val variables = listOf(
            "Food" to db.sumofcatgory("Food"),
            "Transport" to db.sumofcatgory("Transport"),
            "Shopping" to db.sumofcatgory("Shopping"),
            "Bills" to db.sumofcatgory("Bills"),
            "Entertainment" to db.sumofcatgory("Entertainment"),
            "Medicine" to db.sumofcatgory("Medicine"),
            "Other" to db.sumofcatgory("Other")
        )

        val maxEntry = variables.maxByOrNull { it.second }

        if (maxEntry != null) {
            val (highestCategory, highestValue) = maxEntry
            val highestValuestring = "₹$highestValue".toString()
            tvHighstCategoryType.setText(highestCategory)
            tvHighstCategoryAmount.setText(highestValuestring)
        }

        // piechart for catagory

        val piePieChartCategory = findViewById<AAChartView>(R.id.piechartcategoryPIE)
        val loadingCategory = findViewById<ProgressBar>(R.id.chartCategoryLoading)
        loadingCategory.visibility = View.VISIBLE

        lifecycleScope.launch {
            delay(1000)

            val PieChartCategoryvalue = AAChartModel()
                .chartType(AAChartType.Pie)
                .backgroundColor("#181A20")
                .dataLabelsEnabled(true)
                .colorsTheme(
                    arrayOf(
                        "#FF7043",
                        "#29B6F6",
                        "#AB47BC",
                        "#FFA726",
                        "#66BB6A",
                        "#EF5350",
                        "#BDBDBD"
                    )
                )
                .series(
                    arrayOf(
                        AASeriesElement()
                            .data(
                                arrayOf(
                                    arrayOf("Food", db.sumofcatgory("Food")),
                                    arrayOf("Transport", db.sumofcatgory("Transport")),
                                    arrayOf("Shopping", db.sumofcatgory("Shopping")),
                                    arrayOf("Bills", db.sumofcatgory("Bills")),
                                    arrayOf("Entertainment", db.sumofcatgory("Entertainment")),
                                    arrayOf("Medicine", db.sumofcatgory("Medicine")),
                                    arrayOf("Other", db.sumofcatgory("Other"))
                                )
                            )
                    )
                )

            piePieChartCategory.aa_drawChartWithChartModel(PieChartCategoryvalue)
            loadingCategory.visibility = View.GONE
        }

        // piechart for paymenttype

        piePieChartPayment = findViewById(R.id.piechartpaymentPIE)
        val loadingCategorypm = findViewById<ProgressBar>(R.id.chartPaymentLoading)
        loadingCategory.visibility = View.VISIBLE

        lifecycleScope.launch {
            delay(1100)

            val piePieChartPaymentvalue = AAChartModel()
                .chartType(AAChartType.Pie)
                .backgroundColor("#181A20")
                .dataLabelsEnabled(true)
                .colorsTheme(
                    arrayOf(
                        "#4CAF50",
                        "#2196F3",
                        "#FFC107",
                        "#FF5722"
                    )
                )
                .series(
                    arrayOf(
                        AASeriesElement()
                            .data(
                                arrayOf(
                                    arrayOf("Cash", db.sumofpaymenttype("Cash")),
                                    arrayOf("Card", db.sumofpaymenttype("Card")),
                                    arrayOf("UPI", db.sumofpaymenttype("UPI")),
                                    arrayOf("Net Banking", db.sumofpaymenttype("Net Banking"))
                                )
                            )
                    )
                )

            piePieChartPayment.aa_drawChartWithChartModel(piePieChartPaymentvalue)
            loadingCategorypm.visibility = View.GONE
        }


    }

    override fun onResume() {
        super.onResume()
        btnExpenseHistry = findViewById(R.id.expensehistryBTN)
        btnExpenseHistry.setOnClickListener {
            val EHintent = Intent(this, expensehistory::class.java)
            startActivity(EHintent)
        }
    }

}