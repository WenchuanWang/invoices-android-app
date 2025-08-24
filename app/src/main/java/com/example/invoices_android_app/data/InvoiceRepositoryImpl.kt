package com.example.invoices_android_app.data

import com.example.invoices_android_app.domain.InvoiceRepository
import com.example.invoices_android_app.domain.InvoiceResult
import com.example.invoices_android_app.data.remote.InvoiceApiService
import com.example.invoices_android_app.data.mapper.toInvoiceList
import com.google.gson.stream.MalformedJsonException
import javax.inject.Inject

class InvoiceRepositoryImpl @Inject constructor(
    private val apiService: InvoiceApiService
): InvoiceRepository {
    override suspend fun getInvoiceList(path: String): InvoiceResult =
        try {
            val response = apiService.getInvoiceList(path)
            if (response.items.isEmpty()) {
                InvoiceResult.EmptyInvoiceError
            } else {
                InvoiceResult.Success(response.toInvoiceList())
            }
        } catch (e: MalformedJsonException) {
            InvoiceResult.MalformedInvoiceError
        } catch (e: Exception) {
            InvoiceResult.GeneralInvoiceError
        }
}
