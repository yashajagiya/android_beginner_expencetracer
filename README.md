# Expense Tracker

> **Note:** This is a fun personal project made during my Android learning journey. I built it for personal use and to learn more about Android development.

Easily track your daily expenses and understand your spending habits. This is a simple, private, and modern Android app.

---

## ✨ Features

- **Add Expenses:** Quickly log your spending by amount, category, payment method, and date.
- **See Analytics:** View your total and average daily expenses. See which category you spend the most on. Visual charts make it easy to understand.
- **Expense History:** Browse all your expenses. Filter by category to find what you need.
- **Beautiful Design:** Clean, dark-themed interface that's easy on the eyes.
- **Private & Secure:** All your data stays on your device. Nothing is shared or uploaded.

---

## 🔒 Privacy

- **Your privacy matters.**
- All expense data is stored **only on your device** using local storage (SQLite database).
- The app does **not** collect, share, or upload any personal or financial information.

---

## 🚀 Getting Started

### What You Need
- Android Studio (latest version recommended)
- An Android device or emulator (Android 8.0 / API 26 or higher)

### How to Install
1. **Clone this repository:**
   ```bash
   git clone <repo-url>
   ```
2. **Open in Android Studio:**
   - Go to File > Open, then select this project folder.
3. **Build the project:**
   - Wait for Gradle to finish syncing and downloading dependencies.
4. **Run the app:**
   - Choose your device or emulator and press Run.

---

## 📁 Project Overview

- **Main Screen:** Add new expenses easily.
- **Analytics:** See charts and stats about your spending.
- **History:** View and filter all your past expenses.
- **Local Database:** Keeps your data safe and private.

---

## 🛠️ Main Files

- `MainActivity.kt` – Add expenses
- `Analytics.kt` – View analytics and charts
- `expensehistory.kt` – See and filter expense history
- `DataBaseHelper.kt` – Handles local database
- `expencedataadepter.kt` – Shows expenses in a list
- `expensedbvar.kt` – Expense data model

---

## 📦 Built With

- AndroidX
- Material Components
- AAChartCore (for charts)
- MotionToast (for notifications)
- Kotlin

---

## 🤝 Contributing

- Contributions are welcome!
- For big changes, please open an issue first to discuss your idea.
- Special thanks to **AI Assistant** for help with database setup and many more improvements.

---

## 📄 License

This project is licensed under the MIT License.

---

## 🙏 Acknowledgements

- [AAChartCore-Kotlin](https://github.com/AAChartModel/AAChartCore-Kotlin)
- [MotionToast](https://github.com/Spikeysanju/MotionToast) 
