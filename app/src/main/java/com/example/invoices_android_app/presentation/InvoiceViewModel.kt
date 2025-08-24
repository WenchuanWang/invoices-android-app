package com.example.invoices_android_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invoices_android_app.domain.Invoice
import com.example.invoices_android_app.domain.InvoiceRepository
import com.example.invoices_android_app.domain.InvoiceResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class InvoiceType(val label: String, val path: String) {
    NORMAL("Normal", "invoices.json"),
    EMPTY("Empty", "invoices_empty.json"),
    MALFORMED("Malformed", "invoices_malformed.json")
}

sealed class InvoiceError {
    data object EmptyInvoiceError : InvoiceError()
    data object MalformedInvoiceError : InvoiceError()
    data object GeneralInvoiceError : InvoiceError()
}

sealed class InvoiceUiState {
    data object Loading : InvoiceUiState()
    data class Success(val invoiceList: List<Invoice>) : InvoiceUiState()
    data class Error(val error: InvoiceError) : InvoiceUiState()
}

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val repository: InvoiceRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<InvoiceUiState>(InvoiceUiState.Loading)
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()
    
    init {
        loadInvoiceList(InvoiceType.NORMAL)
    }
    
    fun loadInvoiceList(invoiceType: InvoiceType) {
        _uiState.value = InvoiceUiState.Loading

        viewModelScope.launch {
            _uiState.value = when (val result = repository.getInvoiceList(invoiceType.path)) {
                is InvoiceResult.Success -> InvoiceUiState.Success(result.invoiceList)
                is InvoiceResult.EmptyInvoiceError -> InvoiceUiState.Error(InvoiceError.EmptyInvoiceError)
                is InvoiceResult.MalformedInvoiceError -> InvoiceUiState.Error(InvoiceError.MalformedInvoiceError)
                is InvoiceResult.GeneralInvoiceError -> InvoiceUiState.Error(InvoiceError.GeneralInvoiceError)
            }
        }
    }
}
