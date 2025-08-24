package com.example.invoices_android_app.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.format.DateTimeParseException

@RunWith(RobolectricTestRunner::class)
class ExtensionsTest {

    @Test
    fun `formatDateTimeForDisplay should format valid ISO datetime strings correctly`() {
        assertEquals("01/10/2022 10:22", "2022-10-01T10:22:32".formatDateTimeForDisplay())
        assertEquals("05/10/2022 13:00", "2022-10-05T13:00:10".formatDateTimeForDisplay())
        assertEquals("25/12/2022 00:00", "2022-12-25T00:00:00".formatDateTimeForDisplay())
        assertEquals("01/01/2023 23:59", "2023-01-01T23:59:59".formatDateTimeForDisplay())
        assertEquals("31/12/2022 10:00", "2022-12-31T10:00:00".formatDateTimeForDisplay())
    }

    fun `formatDateTimeForDisplay should throw exception for invalid date format`() {
        // Given
        val invalidDate = "invalid-date-format"

        // When & Then
        assertThrows(DateTimeParseException::class.java) {
            invalidDate.formatDateTimeForDisplay()
        }
    }

    @Test
    fun `formatPriceInCentsForDisplay should format valid cent amounts correctly`() {
        assertEquals("$0.00", 0.formatPriceInCentsForDisplay())
        assertEquals("$0.01", 1.formatPriceInCentsForDisplay())
        assertEquals("$999,999.99", 99999999.formatPriceInCentsForDisplay())
        assertEquals("-$1.00", (-100).formatPriceInCentsForDisplay())
    }
}
