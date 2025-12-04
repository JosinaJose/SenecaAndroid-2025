package com.bookinfo.cashregisterapp.Navigation

sealed class NavItem(val route: String, val title: String) {
    object CashRegister : NavItem("cash_register", "Cash Register")
    object PurchaseHistory : NavItem("purchase_history", "Purchase History")
}