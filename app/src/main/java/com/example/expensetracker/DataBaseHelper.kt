package com.example.expensetracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, Datbasename, null, Datbaseversion) {
    companion object {
        private const val Datbasename = "ExpenseTrackerDB.db"
        private const val Datbaseversion = 1
        private const val Tablename = "exptrack"
        private const val columns_ID = "id"
        private const val columns_amount = "amount"
        private const val columns_category = "category"
        private const val columns_paymentmethod = "paymentmethod"
        private const val columns_year = "year"
        private const val columns_month = "month"
        private const val columns_day = "day"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createtableQuery =
            "CREATE TABLE $Tablename($columns_ID INTEGER PRIMARY KEY,$columns_amount INTEGER,$columns_category TEXT,$columns_paymentmethod TEXT,$columns_year INTEGER,$columns_month INTEGER,$columns_day INTEGER)"
        db?.execSQL(createtableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $Tablename")
        onCreate(db)
    }

    fun insertExpense(expensedbvar: expensedbvar) {
        try {
            val db = writableDatabase
            val value = ContentValues().apply {
                put(columns_amount, expensedbvar.amount)
                put(columns_category, expensedbvar.category)
                put(columns_paymentmethod, expensedbvar.paymentmethod)
                put(columns_year, expensedbvar.year)
                put(columns_month, expensedbvar.month)
                put(columns_day, expensedbvar.day)
            }
            db.insert(Tablename, null, value)
            db.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getAllexpense(): List<expensedbvar> {
        val expenselist = mutableListOf<expensedbvar>()
        val dbread = readableDatabase
        val readquery = "SELECT * FROM $Tablename ORDER BY $columns_year DESC, $columns_month DESC, $columns_day DESC, $columns_ID DESC"
        val cursor = dbread.rawQuery(readquery, null)

        while (cursor.moveToNext()) {
            val amount = cursor.getInt(cursor.getColumnIndexOrThrow(columns_amount))
            val category = cursor.getString(cursor.getColumnIndexOrThrow(columns_category))
            val paymentmethod =
                cursor.getString(cursor.getColumnIndexOrThrow(columns_paymentmethod))
            val year = cursor.getInt(cursor.getColumnIndexOrThrow(columns_year))
            val month = cursor.getInt(cursor.getColumnIndexOrThrow(columns_month))
            val day = cursor.getInt(cursor.getColumnIndexOrThrow(columns_day))

            val expensedbvar = expensedbvar(0, amount, category, paymentmethod, year, month, day)
            expenselist.add(expensedbvar)
        }
        cursor.close()
        dbread.close()
        return expenselist
    }

    fun getExpensesByCategory(category: String): List<expensedbvar> {
        val expenselist = mutableListOf<expensedbvar>()
        val dbread = readableDatabase
        val readquery = "SELECT * FROM $Tablename WHERE $columns_category = ? ORDER BY $columns_year DESC, $columns_month DESC, $columns_day DESC, $columns_ID DESC"
        val cursor = dbread.rawQuery(readquery, arrayOf(category))

        while (cursor.moveToNext()) {
            val amount = cursor.getInt(cursor.getColumnIndexOrThrow(columns_amount))
            val category = cursor.getString(cursor.getColumnIndexOrThrow(columns_category))
            val paymentmethod =
                cursor.getString(cursor.getColumnIndexOrThrow(columns_paymentmethod))
            val year = cursor.getInt(cursor.getColumnIndexOrThrow(columns_year))
            val month = cursor.getInt(cursor.getColumnIndexOrThrow(columns_month))
            val day = cursor.getInt(cursor.getColumnIndexOrThrow(columns_day))

            val expensedbvar = expensedbvar(0, amount, category, paymentmethod, year, month, day)
            expenselist.add(expensedbvar)
        }
        cursor.close()
        dbread.close()
        return expenselist
    }

    fun sumofcatgory(category: String): Int {
        val dbread = readableDatabase
        val readquery = "SELECT SUM($columns_amount) FROM $Tablename WHERE $columns_category = ?"
        val cursor = dbread.rawQuery(readquery, arrayOf(category))
        var total: Int = 0
        while (cursor.moveToNext()) {
            total = cursor.getInt(0)
        }
        cursor.close()
        dbread.close()
        return total
    }

    fun sumofpaymenttype(category: String): Int {
        val dbread = readableDatabase
        val readquery =
            "SELECT SUM($columns_amount) FROM $Tablename WHERE $columns_paymentmethod = ?"
        val cursor = dbread.rawQuery(readquery, arrayOf(category))
        var total: Int = 0
        while (cursor.moveToNext()) {
            total = cursor.getInt(0)
        }
        cursor.close()
        dbread.close()
        return total
    }

    fun sumofexpence(): Int {
        val dbread = readableDatabase
        val readquery = "SELECT SUM($columns_amount) FROM $Tablename"
        val cursor = dbread.rawQuery(readquery, null)
        var total: Int = 0
        while (cursor.moveToNext()) {
            total = cursor.getInt(0)
        }
        cursor.close()
        dbread.close()
        return total
    }

    fun todayExpense(): Int {
        val db = readableDatabase
        val today = LocalDate.now()
        val year = today.year
        val month = today.monthValue
        val day = today.dayOfMonth

        val query =
            """SELECT SUM($columns_amount)FROM $Tablename WHERE $columns_year = ? AND $columns_month = ? AND $columns_day = ?"""
        val cursor = db.rawQuery(query, arrayOf(year.toString(), month.toString(), day.toString()))
        var total = 0
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return total
    }


}
