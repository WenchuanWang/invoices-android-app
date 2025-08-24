package com.example.invoices_android_app.domain

sealed class InvoiceResult {
    data class Success(val invoiceList: List<Invoice>) : InvoiceResult()
    data object EmptyInvoiceError : InvoiceResult()
    data object MalformedInvoiceError : InvoiceResult()
    data object GeneralInvoiceError : InvoiceResult()
}
