package com.example.expensetracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class expensehistory : AppCompatActivity() {

    private lateinit var db: DataBaseHelper
    private lateinit var spinCategoryHistory: Spinner
    private lateinit var adapterexpense: Expencedataadepter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_expensehistory)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = DataBaseHelper(this)

        recyclerView = findViewById(R.id.recyclerView)
        spinCategoryHistory = findViewById(R.id.categoryexpenceSPIN)

        val categoryOfExpensehistory = listOf(
            "All", "Food", "Transport", "Shopping", "Bills", "Entertainment", "Medicine", "Other"
        )

        val categoryAdapter = ArrayAdapter(this, R.layout.spinner_item, categoryOfExpensehistory)
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinCategoryHistory.adapter = categoryAdapter


        val expenseList = db.getAllexpense()
        adapterexpense = Expencedataadepter(expenseList)
        

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterexpense

        spinCategoryHistory.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedCategory = parent?.getItemAtPosition(position).toString()
                val filteredList = if (selectedCategory == "All") {
                    db.getAllexpense()
                } else {
                    db.getExpensesByCategory(selectedCategory)
                }
                adapterexpense.refreshdata(filteredList)
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {

            }
        }
    }
}
