package com.example.invoices_android_app.data

import com.google.gson.annotations.SerializedName

data class InvoiceListResponse(
    @SerializedName("items")
    val items: List<InvoiceResponse>
)

data class InvoiceResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("items")
    val items: List<InvoiceItemResponse>,
)

data class InvoiceItemResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("priceinCents")
    val priceInCents: Int
)
