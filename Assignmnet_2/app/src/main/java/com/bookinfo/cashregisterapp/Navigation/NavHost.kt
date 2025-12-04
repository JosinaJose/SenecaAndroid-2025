package com.bookinfo.cashregisterapp.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bookinfo.cashregisterapp.CashRegisterViewModel
import com.bookinfo.cashregisterapp.Screens.CashRegisterScreen
import com.bookinfo.cashregisterapp.Screens.PurchaseHistoryScreen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val cashVM: CashRegisterViewModel = viewModel() // Create once, share everywhere

    NavHost(navController = navController, startDestination = "cash_register") {
        composable("cash_register") {
            CashRegisterScreen(
                cashVM = cashVM, // Use shared instance
                navController = navController
            )
        }
        composable("purchase_history") {
            PurchaseHistoryScreen(
                cashVM = cashVM, // Use same shared instance
                navController = navController
            )
        }
    }
}