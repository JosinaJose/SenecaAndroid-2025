package com.bookinfo.cashregisterapp


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ProductItems(
    val productName: String,
    val price: Double,
    val quantity: Int
)

data class PurchasedItem(
    val productName: String,
    val price: Double,
    val quantity: Int,
    val timestamp: String
)

class CashRegisterViewModel : ViewModel() {

    var productList by mutableStateOf(
        listOf(
            ProductItems("Laptop Charger", 35.50, 50),
            ProductItems("Bluetooth Mouse", 15.00, 120),
            ProductItems("USB-C Cable", 7.99, 300),
            ProductItems("Gaming Headset", 65.99, 80),
            ProductItems("Monitor Stand", 22.50, 45),
            ProductItems("Wireless Keyboard", 29.99, 60),
            ProductItems("External Hard Drive", 79.99, 40),
            ProductItems("HDMI Cable", 12.50, 150),
            ProductItems("Webcam 1080p", 49.99, 30),
            ProductItems("Portable Charger", 25.00, 70),
            ProductItems("Smartphone Stand", 9.99, 100),
            ProductItems("Laptop Sleeve", 19.99, 55),
            ProductItems("Noise-Canceling Earbuds", 89.99, 25),
            ProductItems("LED Desk Lamp", 32.50, 40),
            ProductItems("USB Hub", 14.99, 80)
        )
    )

    var selectedIndex by mutableStateOf<Int?>(null)
    var selectedQuantity by mutableStateOf(0)
    var enteredQuantity by mutableStateOf("")

    var errorMessage by mutableStateOf("")
        private set

    // List to track purchased items
    var purchasedItems by mutableStateOf<List<PurchasedItem>>(emptyList())

    fun setError(msg: String) {
        errorMessage = msg
    }

    fun clearError() {
        errorMessage = ""
    }

    fun selectProduct(index: Int) {
        selectedIndex = index
        selectedQuantity = 0
        enteredQuantity = ""
    }

    fun onKeyPress(key: String) {
        when (key) {
            "C" -> enteredQuantity = ""
            else -> enteredQuantity += key
        }
        selectedQuantity = enteredQuantity.toIntOrNull() ?: 0
    }

    fun totalAmount(): Double {
        val price = selectedIndex?.let { productList[it].price } ?: 0.0
        return price * selectedQuantity
    }

    fun Purchase(): Boolean {
        selectedIndex?.let { index ->
            if (selectedQuantity > 0) {
                val product = productList[index]

                // Check if there's enough stock
                if (selectedQuantity <= product.quantity) {
                    // Get current timestamp
                    val sdf = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
                    val currentTime = sdf.format(Date())

                    // Add to purchased items
                    val purchasedItem = PurchasedItem(
                        productName = product.productName,
                        price = product.price,
                        quantity = selectedQuantity,
                        timestamp = currentTime
                    )
                    purchasedItems = purchasedItems + purchasedItem

                    // Update stock
                    productList = productList.toMutableList().apply {
                        this[index] = product.copy(quantity = product.quantity - selectedQuantity)
                    }

                    // Reset selections
                    selectedIndex = null
                    selectedQuantity = 0
                    enteredQuantity = ""

                    return true // Purchase successful
                }
            }
        }
        return false // Purchase failed
    }
}