package com.example.invoices_android_app.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.invoices_android_app.R
import com.example.invoices_android_app.domain.Invoice
import com.example.invoices_android_app.domain.InvoiceItem
import com.example.invoices_android_app.presentation.InvoiceError
import com.example.invoices_android_app.presentation.InvoiceType
import com.example.invoices_android_app.presentation.InvoiceUiState
import com.example.invoices_android_app.presentation.InvoiceViewModel
import com.example.invoices_android_app.presentation.ui.components.ErrorMessage
import com.example.invoices_android_app.presentation.ui.components.LoadingSpinner

@Composable
fun InvoiceListScreen(
    viewModel: InvoiceViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        InvoiceActionButtonSection { type ->
            viewModel.loadInvoiceList(type)
        }

        when(val state = uiState) {
             is InvoiceUiState.Loading -> {
                LoadingSpinner()
            }
            is InvoiceUiState.Error -> {
                when (state.error) {
                    is InvoiceError.EmptyInvoiceError -> {
                        ErrorMessage(
                            message = stringResource(R.string.error_empty_invoices),
                            onRetry = { viewModel.loadInvoiceList(InvoiceType.NORMAL) }
                        )
                    }
                    is InvoiceError.MalformedInvoiceError -> {
                        ErrorMessage(
                            message = stringResource(R.string.error_malformed_json),
                            onRetry = { viewModel.loadInvoiceList(InvoiceType.NORMAL) }
                        )
                    }
                    else -> {
                        ErrorMessage(
                            message = stringResource(R.string.error_general),
                            onRetry = { viewModel.loadInvoiceList(InvoiceType.NORMAL) }
                        )
                    }
                }
            }
            is InvoiceUiState.Success -> {
                InvoiceContent(
                    invoices = state.invoiceList
                )
            }
        }
    }
}

@Composable
private fun InvoiceActionButtonSection(
    onClick: (InvoiceType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        InvoiceType.entries.forEach { type ->
            Button(
                onClick = { onClick(type) },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = type.label,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}

@Composable
private fun InvoiceContent(
    invoices: List<Invoice>
) {
    LazyColumn {
        items(invoices) { invoice ->
            InvoiceCard(
                invoice = invoice
            )
        }
    }
}

@Composable
private fun InvoiceCard(
    invoice: Invoice,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.label_invoice, invoice.id),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = invoice.date,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )

            Column {
                invoice.items.forEach { item ->
                    InvoiceItemCard(item)
                }
            }
        }
    }
}

@Composable
private fun InvoiceItemCard(
    item: InvoiceItem
) {

    Text(
        text = item.name,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 4.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.label_quantity_hours, item.quantity),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = stringResource(R.string.label_per_hour, item.price),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
