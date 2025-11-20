package com.example.takecare.ui.Utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun convertMillisToDate(millis: Long?): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis!!))
}

fun formatLocalDateTime(dateString: String): String {
    return try {
        val date = LocalDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM yyyy", Locale("es", "MX"))
        date.format(formatter)
    } catch (e: Exception) {
        "Fecha inválida"
    }
}