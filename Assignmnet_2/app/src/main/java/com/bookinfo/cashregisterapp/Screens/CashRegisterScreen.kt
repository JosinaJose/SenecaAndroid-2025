package com.bookinfo.cashregisterapp.Screens


/*
CashRegisterScreen.kt
This file contains all UI components for the Cash Register screen, including:
- Top bar
- Selected product display
- Quantity keypad
- Total amount area with BUY button
- Dynamic quantity display
- Non-scrolling product list

  The screen interacts with CashRegisterViewModel to update selected items,
  quantities, validation messages, and purchase history navigation.
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bookinfo.cashregisterapp.CashRegisterViewModel
import com.bookinfo.cashregisterapp.ProductItems


@Composable
fun CashRegisterScreen(
    cashVM: CashRegisterViewModel = viewModel(),
    navController: NavController
) {
    val selectedIndex = cashVM.selectedIndex
    val selectedItem = selectedIndex?.let { cashVM.productList[it].productName } ?: ""

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        item { TopBar() }

        // Item name
        item { SelectedItemTextBox(selectedItem = selectedItem) }

        // Quantity keypad
        item {
            QuantityKeypad(
                cashVM = cashVM,
                onNavigateToHistory = {
                    navController.navigate("purchase_history")
                }
            )
        }

        // Selected quantity
        item { SelectedItemQuantityDynamic(cashVM = cashVM) }

        // Product list
        itemsIndexed(cashVM.productList) { index, product ->
            ProductItemRow(
                product = product,
                isSelected = selectedIndex == index,
                onClick = { cashVM.selectProduct(index) }
            )
        }
    }
}


// -------------------- TOP BAR --------------------
@Composable
fun TopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFF008080)),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Assignment_2",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

// -------------------- SELECTED ITEM TEXT BOX --------------------
@Composable
fun SelectedItemTextBox(selectedItem: String, topPadding: Int = 16) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(top = topPadding.dp, bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (selectedItem.isNotEmpty()) selectedItem else "Product Type",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

// -------------------- TOTAL DISPLAY --------------------
@Composable
fun TotalDisplay(amount: String?) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(30.dp)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = amount ?: "Total",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

// -------------------- CASH BUTTON --------------------
@Composable
fun CashButton(
    label: String,
    modifier: Modifier = Modifier,
    isOval: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        shape = if (isOval) RoundedCornerShape(50) else RoundedCornerShape(8.dp),
        modifier = modifier,
        enabled = enabled,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = Color(0xFF008080)
        )
    ) {
        Text(text = label, fontSize = 20.sp, color = Color.Black)
    }
}

// -------------------- QUANTITY KEYPAD --------------------
@Composable
fun QuantityKeypad(
    cashVM: CashRegisterViewModel,
    onNavigateToHistory: () -> Unit
) {
    val rows = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("C", "0")
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Number buttons
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            for (row in rows) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (key in row) {
                        CashButton(
                            label = key,
                            modifier = Modifier.weight(1f)
                        ) {
                            cashVM.onKeyPress(key)
                        }
                    }
                }
            }
        }

        // Total display + BUY button
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            TotalDisplay(amount = "$${String.format("%.2f", cashVM.totalAmount())}")
            BuyButton(cashVM = cashVM, onNavigateToHistory = onNavigateToHistory)
        }
    }
}

// -------------------- BUY BUTTON AND VALIDATION --------------------
@Composable
fun BuyButton(
    cashVM: CashRegisterViewModel,
    onNavigateToHistory: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Shows error message from ViewModel (survives rotation)
        if (cashVM.errorMessage.isNotEmpty()) {
            Text(
                text = cashVM.errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(90.dp)
            )
        }

        CashButton(
            label = "BUY",
            isOval = false,
            modifier = Modifier
                .height(150.dp)
                .width(90.dp)
        ) {
            val selectedIndex = cashVM.selectedIndex
            val selectedQty = cashVM.selectedQuantity

            // Validation ─ no product or quantity
            if (selectedIndex == null || selectedQty <= 0) {
                cashVM.setError("Select product & quantity")
                return@CashButton
            }

            val availableQty = cashVM.productList[selectedIndex].quantity
            if (selectedQty > availableQty) {
                cashVM.setError("Not enough stock ($availableQty)")
                return@CashButton
            }

            // Valid purchase
            val success = cashVM.Purchase()
            if (success) {
                cashVM.clearError()
                onNavigateToHistory()
            }
        }
    }
}

// -------------------- SELECTED ITEM QUANTITY --------------------
@Composable
fun SelectedItemQuantityDynamic(cashVM: CashRegisterViewModel) {
    val quantity = cashVM.selectedQuantity

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = if (quantity > 0) quantity.toString() else "Quantity",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            textAlign = TextAlign.Left
        )
    }
}

// -------------------- PRODUCT LIST --------------------
@Composable
fun ProductItemRow(
    product: ProductItems,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = product.productName,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
            Text(
                text = "${product.price}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Text(
            text = "${product.quantity}",
            fontSize = 16.sp
        )
    }
}