package com.example.invoices_android_app.domain

interface InvoiceRepository {
    suspend fun getInvoiceList(path: String): InvoiceResult
}
