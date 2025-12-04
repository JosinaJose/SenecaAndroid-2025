package com.bookinfo.cashregisterapp.Screens
/*
Screen showing a list of purchased items.
Displays a top app bar with a back button and the purchase history list.
*/

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bookinfo.cashregisterapp.CashRegisterViewModel
import com.bookinfo.cashregisterapp.PurchasedItem
import com.bookinfo.cashregisterapp.R


@Composable
fun PurchaseHistoryScreen(
    cashVM: CashRegisterViewModel=viewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top bar with a back button
        TopBarWithBackBtn { navController.popBackStack() }

        // List of purchased items retrieved from ViewModel
            PurchasedItemList(purchasedItems = cashVM.purchasedItems)

    }
}

/*
  Displays a vertically scrollable list of purchased items.
 */
// -------------------- COMPOSABLE: PURCHASED ITEM LIST --------------------
@Composable
fun PurchasedItemList(purchasedItems: List<PurchasedItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(purchasedItems) { item ->
            PurchaseItemRow(item) // Row UI for each purchased item
            // Divider Between Items
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.LightGray
            )
        }
    }
}
/*
  Displays a single purchased item including:
   Product name
  Total cost
 Price × Quantity
  Timestamp
 */

// -------------------- COMPOSABLE: PURCHASE ITEM ROW --------------------
@Composable
fun PurchaseItemRow(item: PurchasedItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // First row: Product name and total price
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = item.productName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            // Total = price × quantity

            Text(
                text = "$${String.format("%.2f", item.price * item.quantity)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Second row: Price details and timestamp
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$${String.format("%.2f", item.price)} × ${item.quantity}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Text(
                text = item.timestamp,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
/*
  Simple top bar with:
   Back button icon
  Title text ("Assignment_2")
 */
// -------------------- COMPOSABLE: TOP BAR WITH BACK BUTTON --------------------
@Composable
fun TopBarWithBackBtn(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFF008080)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Assignment_2",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}
