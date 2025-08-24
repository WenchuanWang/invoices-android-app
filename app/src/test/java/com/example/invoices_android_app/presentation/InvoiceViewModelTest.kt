package com.example.invoices_android_app.presentation

import com.example.invoices_android_app.MainDispatcherRule
import com.example.invoices_android_app.domain.Invoice
import com.example.invoices_android_app.domain.InvoiceItem
import com.example.invoices_android_app.domain.InvoiceRepository
import com.example.invoices_android_app.domain.InvoiceResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InvoiceViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: InvoiceRepository
    private lateinit var viewModel: InvoiceViewModel

    @Before
    fun setUp() {
        repository = mockk()

        val path = "invoices.json"
        val testInvoice = listOf(
            createTestInvoice("inv001", "01/10/2022 10:22"),
            createTestInvoice("inv002", "10/10/2022 23:30")
        )
        coEvery { repository.getInvoiceList(path) }.returns(InvoiceResult.Success(testInvoice))
        viewModel = InvoiceViewModel(repository)
    }

    @Test
    fun `Given invoices are available When ViewModel is initialized Then state should be updated with invoices`() = runTest {
        // When - wait for VM has been initialised
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value is InvoiceUiState.Success)
        val value = viewModel.uiState.value as InvoiceUiState.Success
        assertEquals(2, value.invoiceList.size)
        assertEquals(2, value.invoiceList.size)
        assertEquals("inv001", value.invoiceList[0].id)
        assertEquals("inv002", value.invoiceList[1].id)
        assertEquals("01/10/2022 10:22", value.invoiceList[0].date)
        assertEquals(2, value.invoiceList[0].items.size)
        assertEquals("Service #1", value.invoiceList[0].items[0].name)
        assertEquals("1", value.invoiceList[0].items[0].quantity)
        assertEquals("$7.50", value.invoiceList[0].items[0].price)
        assertEquals("$10.00", value.invoiceList[1].items[1].price)
    }

    @Test
    fun `Given repository returns empty list When loadInvoiceList is called Then state should be EmptyInvoiceError`() = runTest {
        // Given
        val path = "invoices_empty.json"
        coEvery { repository.getInvoiceList(path) }.returns(InvoiceResult.EmptyInvoiceError)

        // When
        viewModel.loadInvoiceList(InvoiceType.EMPTY)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value is InvoiceUiState.Error)
        val value = viewModel.uiState.value as InvoiceUiState.Error
        assertTrue(value.error is InvoiceError.EmptyInvoiceError)
    }

    @Test
    fun `Given repository returns malformed json error When loadInvoiceList is called Then state should be MalformedInvoiceError`() = runTest {
        // Given
        val path = "invoices_malformed.json"
        coEvery { repository.getInvoiceList(path) }.returns(InvoiceResult.MalformedInvoiceError)

        // When
        viewModel.loadInvoiceList(InvoiceType.MALFORMED)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value is InvoiceUiState.Error)
        val value = viewModel.uiState.value as InvoiceUiState.Error
        assertTrue(value.error is InvoiceError.MalformedInvoiceError)
    }

    @Test
    fun `Given repository returns general error When loadInvoiceList is called Then state should be GeneralInvoiceError`() = runTest {
        // Given
        val path = "invoices.json"
        coEvery { repository.getInvoiceList(path) }.returns(InvoiceResult.GeneralInvoiceError)

        // When
        viewModel.loadInvoiceList(InvoiceType.NORMAL)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value is InvoiceUiState.Error)
        val value = viewModel.uiState.value as InvoiceUiState.Error
        assertTrue(value.error is InvoiceError.GeneralInvoiceError)
    }

    private fun createTestInvoice(
        id: String,
        date: String
    ): Invoice = Invoice(
        id = id,
        date = date,
        items = listOf(
            createTestInvoiceItem("item1", "Service #1", "1", "$7.50"),
            createTestInvoiceItem("item2", "Service #2", "3", "$10.00")
        )
    )

    private fun createTestInvoiceItem(
        id: String,
        name: String,
        quantity: String = "1",
        priceInCents: String = "$7.50"
    ): InvoiceItem = InvoiceItem(
        id = id,
        name = name,
        quantity = quantity,
        price = priceInCents
    )
}
