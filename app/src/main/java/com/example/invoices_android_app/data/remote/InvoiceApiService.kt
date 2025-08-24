package com.example.invoices_android_app.data.remote

import com.example.invoices_android_app.data.InvoiceListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface InvoiceApiService {
    @GET("xmm-homework/{path}")
    suspend fun getInvoiceList(@Path("path") path: String): InvoiceListResponse
}
