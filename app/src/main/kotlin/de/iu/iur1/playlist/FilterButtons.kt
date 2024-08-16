package de.iu.iur1.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.iu.iur1.OutlinedBox
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Filter { TODAY, YESTERDAY, TWO_DAYS_AGO, SELECTED }

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FilterButtons(onSelectFilter: (LocalDate) -> Unit) {
    var currentFilter by remember { mutableStateOf(Filter.TODAY) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    OutlinedBox {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            InputChip(
                selected = currentFilter == Filter.TODAY,
                onClick = {
                    currentFilter = Filter.TODAY
                    onSelectFilter.invoke(LocalDate.now())
                },
                label = { Text(text = "Heute") })
            InputChip(
                selected = currentFilter == Filter.YESTERDAY,
                onClick = {
                    currentFilter = Filter.YESTERDAY
                    onSelectFilter.invoke(nowMinusDays(1))
                },
                label = { Text(text = "Gestern") })
            InputChip(
                selected = currentFilter == Filter.TWO_DAYS_AGO,
                onClick = {
                    currentFilter = Filter.TWO_DAYS_AGO
                    onSelectFilter.invoke(nowMinusDays(2))
                },
                label = { Text(text = nowMinusDays(2).displayString()) })
            OutlinedSelectableDatePicker(
                selected = currentFilter == Filter.SELECTED,
                onClick = { showDatePicker = true })
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Abbrechen")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            onSelectFilter.invoke(epochMillisToDate(datePickerState.selectedDateMillis))
                            currentFilter = Filter.SELECTED
                            showDatePicker = false
                        }) {
                            Text("OK")
                        }
                    }) {
                    DatePicker(state = datePickerState)
                }
            }

        }
    }
}

@Composable
fun OutlinedSelectableDatePicker(selected: Boolean, onClick: () -> Unit) = if (selected) {
    FilledIconButton(onClick = onClick) {
        Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "Datum")
    }
} else {
    OutlinedIconButton(onClick = onClick) {
        Icon(imageVector = Icons.Outlined.DateRange, contentDescription = "Datum")
    }
}

fun nowMinusDays(days: Long): LocalDate = LocalDate.now()
    .minusDays(days)

fun LocalDate.displayString(): String = this.format(DateTimeFormatter.ofPattern("dd.MM"))

fun epochMillisToDate(millis: Long?): LocalDate =
    Instant.ofEpochMilli(millis ?: Instant.now().toEpochMilli())
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
