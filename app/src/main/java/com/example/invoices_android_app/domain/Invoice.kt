package com.example.invoices_android_app.domain

data class Invoice(
    val id: String,
    val date: String,
    val items: List<InvoiceItem>
)

data class InvoiceItem(
    val id: String,
    val name: String,
    val quantity: String,
    val price: String
)
