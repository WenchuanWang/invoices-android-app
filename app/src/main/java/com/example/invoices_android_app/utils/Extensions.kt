package com.example.invoices_android_app.utils

import android.icu.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.formatDateTimeForDisplay(): String {
    val parsedDate = LocalDateTime.parse(this)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH)
    return parsedDate.format(formatter)
}

fun Int.formatPriceInCentsForDisplay(): String {
    val priceInDollars = this / 100.0
    val format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-AU"))
    return format.format(priceInDollars)
}
