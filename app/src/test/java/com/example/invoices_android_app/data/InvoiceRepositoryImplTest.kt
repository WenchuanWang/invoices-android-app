package com.example.invoices_android_app.data

import com.example.invoices_android_app.data.remote.InvoiceApiService
import com.example.invoices_android_app.domain.InvoiceRepository
import com.example.invoices_android_app.domain.InvoiceResult
import com.google.gson.stream.MalformedJsonException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.UnknownHostException

@RunWith(RobolectricTestRunner::class)
class InvoiceRepositoryImplTest {

    private lateinit var repository: InvoiceRepository
    private lateinit var mockApiService: InvoiceApiService

    @Before
    fun setUp() {
        mockApiService = mockk()
        repository = InvoiceRepositoryImpl(mockApiService)
    }

    @Test
    fun `Given API returns normal invoices When getInvoiceList is called Then success with invoices should be returned`() = runTest {
        // Given
        val path = "invoices.json"
        val testInvoiceList = listOf(
            createTestInvoiceResponse("inv001", "2022-10-01T10:22:00"),
            createTestInvoiceResponse("inv002", "2022-10-05T13:00:10")
        )
        val apiResponse = InvoiceListResponse(testInvoiceList)

        coEvery { mockApiService.getInvoiceList(path) } returns apiResponse

        // When
        val result = repository.getInvoiceList(path)

        // Then
        assertTrue(result is InvoiceResult.Success)
        val invoiceList = (result as InvoiceResult.Success).invoiceList
        assertEquals(2, invoiceList.size)
        assertEquals("inv001", invoiceList[0].id)
        assertEquals("inv002", invoiceList[1].id)
        assertEquals("01/10/2022 10:22", invoiceList[0].date)
        assertEquals(2, invoiceList[0].items.size)
        assertEquals("Service #1", invoiceList[0].items[0].name)
        assertEquals("1", invoiceList[0].items[0].quantity)
        assertEquals("$5.00", invoiceList[0].items[0].price)
        assertEquals("$7.50", invoiceList[1].items[1].price)
    }

    @Test
    fun `Given API returns empty invoices When getInvoiceList is called Then EmptyInvoiceError should be returned`() = runTest {
        // Given
        val path = "invoices_empty.json"
        val testInvoiceList = emptyList<InvoiceResponse>()
        val apiResponse = InvoiceListResponse(testInvoiceList)

        coEvery { mockApiService.getInvoiceList(path) } returns apiResponse

        // When
        val result = repository.getInvoiceList(path)

        // Then
        assertTrue(result is InvoiceResult.EmptyInvoiceError)
    }

    @Test
    fun `Given API throws MalformedJsonException When getInvoiceList is called Then MalformedInvoiceError should be returned`() = runTest {
        // Given
        val path = "invoices_malformed.json"

        coEvery { mockApiService.getInvoiceList(path) } throws MalformedJsonException("malformed json")

        // When
        val result = repository.getInvoiceList(path)

        // Then
        assertTrue(result is InvoiceResult.MalformedInvoiceError)
    }

    @Test
    fun `Given API throws UnknownHostException When getInvoiceList is called Then GeneralInvoiceError should be returned`() = runTest {
        // Given
        val path = "invoices.json"

        coEvery { mockApiService.getInvoiceList(path) } throws UnknownHostException("network issue")

        // When
        val result = repository.getInvoiceList(path)

        // Then
        assertTrue(result is InvoiceResult.GeneralInvoiceError)
    }

    @Test
    fun `Given API throws RuntimeException When getInvoiceList is called Then GeneralInvoiceError should be returned`() = runTest {
        // Given
        val path = "invoices.json"

        coEvery { mockApiService.getInvoiceList(path) } throws RuntimeException("server error")

        // When
        val result = repository.getInvoiceList(path)

        // Then
        assertTrue(result is InvoiceResult.GeneralInvoiceError)
    }

    private fun createTestInvoiceResponse(
        id: String,
        date: String
    ): InvoiceResponse = InvoiceResponse(
        id = id,
        date = date,
        items = listOf(
            createTestInvoiceItemResponse("item1", "Service #1", 1, 500),
            createTestInvoiceItemResponse("item2", "Service #2", 2, 750)
        )
    )

    private fun createTestInvoiceItemResponse(
        id: String,
        name: String,
        quantity: Int,
        priceInCents: Int
    ): InvoiceItemResponse = InvoiceItemResponse(
        id = id,
        name = name,
        quantity = quantity,
        priceInCents = priceInCents
    )
}
