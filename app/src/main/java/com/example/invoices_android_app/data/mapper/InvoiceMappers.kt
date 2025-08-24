package com.example.invoices_android_app.data.mapper

import com.example.invoices_android_app.data.InvoiceListResponse
import com.example.invoices_android_app.data.InvoiceResponse
import com.example.invoices_android_app.data.InvoiceItemResponse
import com.example.invoices_android_app.domain.Invoice
import com.example.invoices_android_app.domain.InvoiceItem
import com.example.invoices_android_app.utils.formatDateTimeForDisplay
import com.example.invoices_android_app.utils.formatPriceInCentsForDisplay

private fun InvoiceResponse.toInvoiceData(): Invoice = Invoice(
    id = id,
    date = date.formatDateTimeForDisplay(),
    items = items.map { it.toInvoiceItem() }
)

private fun InvoiceItemResponse.toInvoiceItem(): InvoiceItem = InvoiceItem(
    id = id,
    name = name,
    quantity = quantity.toString(),
    price = priceInCents.formatPriceInCentsForDisplay()
)

fun InvoiceListResponse.toInvoiceList(): List<Invoice> = items.map { it.toInvoiceData() }
