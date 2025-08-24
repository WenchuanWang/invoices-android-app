package com.example.invoices_android_app

import com.example.invoices_android_app.domain.Invoice
import com.example.invoices_android_app.domain.InvoiceItem
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `test invoice creation`() {
        val invoiceItem = InvoiceItem(
            id = "item1",
            name = "Test Service",
            quantity = "1",
            price = "$10.00"
        )
        
        val invoice = Invoice(
            id = "inv001",
            date = "Oct 01, 2022 10:22 AM",
            items = listOf(invoiceItem)
        )
        
        assertEquals("inv001", invoice.id)
        assertEquals("Oct 01, 2022 10:22 AM", invoice.date)
        assertEquals(1, invoice.items.size)
        assertEquals("Test Service", invoice.items[0].name)
    }
}