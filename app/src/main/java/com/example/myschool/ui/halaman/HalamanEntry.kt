package com.example.myschool.ui.halaman

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myschool.R
import com.example.myschool.model.DetailSiswa
import com.example.myschool.model.EntryViewModel
import com.example.myschool.model.PenyediaViewModel
import com.example.myschool.model.UIStateSiswa
import com.example.myschool.navigasi.DestinasiNavigasi
import com.example.myschool.navigasi.SiswaTopAppBar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = R.string.entry_siswa
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySiswaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EntryViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiEntry.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        EntrySiswaBody(
            uiStateSiswa = viewModel.uiStateSiswa,
            onSiswaValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveSiswa()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EntrySiswaBody(
    uiStateSiswa: UIStateSiswa,
    onSiswaValueChange: (DetailSiswa) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        FormInputSiswa(
            detailSiswa = uiStateSiswa.detailSiswa,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = uiStateSiswa.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_submit))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputSiswa(
    detailSiswa: DetailSiswa,
    modifier: Modifier = Modifier,
    onValueChange: (DetailSiswa) -> Unit = {},
    enabled: Boolean = true
) {
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }

    val dateFormat = remember { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) }
    val dateString = remember { mutableStateOf(dateFormat.format(selectedDate.time)) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Row {
            OutlinedTextField(
                value = detailSiswa.tanggal,
                onValueChange = { onValueChange(detailSiswa.copy(tanggal = it)) },
                label = { Text(stringResource(R.string.tanggal)) },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = false,
                singleLine = true
            )
            //tanggal
            DatePicker { selectedDate ->
                onValueChange(detailSiswa.copy(tanggal = selectedDate))
            }
        }
        Text(text = "Jam Mulai Pelajaran 1 : ")
            TimeRangePicker(
                startTime = detailSiswa.jammulai1,
                endTime = "",
                onStartTimeSelected = { onValueChange(detailSiswa.copy(jammulai1 = it)) },
                onEndTimeSelected = { "" },
                modifier = Modifier.fillMaxWidth()
            )
        TextField(
            value = detailSiswa.jammulai1,
            onValueChange = { onValueChange(detailSiswa.copy(jammulai1 = it)) },
            label = { Text(stringResource(R.string.jammulai1)) },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = false,
            singleLine = true
        )
        Text(text = "Jam Selesai Pelajaran 1 : ")
        TimeRangePicker(
                startTime = detailSiswa.jamselesai1,
                endTime = "",
                onStartTimeSelected = { onValueChange(detailSiswa.copy(jamselesai1 = it)) },
                onEndTimeSelected = { "" },
                modifier = Modifier.fillMaxWidth()
            )
        TextField(
            value = detailSiswa.jamselesai1,
            onValueChange = { onValueChange(detailSiswa.copy(jamselesai1 = it)) },
            label = { Text(stringResource(R.string.jamselesai1)) },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = false,
            singleLine = true
        )
        Divider()
        Text(text = "Jam Mulai Pelajaran 2 : ")
        TimeRangePicker(
                startTime = detailSiswa.jammulai2,
                endTime = "",
                onStartTimeSelected = { onValueChange(detailSiswa.copy(jammulai2 = it)) },
                onEndTimeSelected = { "" },
                modifier = Modifier.fillMaxWidth())
        TextField(
            value = detailSiswa.jammulai2,
            onValueChange = { onValueChange(detailSiswa.copy(jammulai2 = it)) },
            label = { Text(stringResource(R.string.jammulai2)) },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = false,
            singleLine = true
        )
        Text(text = "Jam Selesai Pelajaran 2 : ")
        TimeRangePicker(
                startTime = detailSiswa.jamselesai2,
                endTime = "",
                onStartTimeSelected = { onValueChange(detailSiswa.copy(jamselesai2 = it)) },
                onEndTimeSelected = { "" },
                modifier = Modifier.fillMaxWidth())
        TextField(
            value = detailSiswa.jamselesai2,
            onValueChange = { onValueChange(detailSiswa.copy(jammulai2 = it)) },
            label = { Text(stringResource(R.string.jamselesai2)) },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = false,
            singleLine = true
        )
        OutlinedTextField(
            value = detailSiswa.jurusan,
            onValueChange = { onValueChange(detailSiswa.copy(jurusan = it)) },
            label = { Text(text = stringResource(R.string.jurusan)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailSiswa.mapel1,
            onValueChange = { onValueChange(detailSiswa.copy(mapel1 = it)) },
            label = { Text(text = stringResource(R.string.mapel1)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailSiswa.mapel2,
            onValueChange = { onValueChange(detailSiswa.copy(mapel2 = it)) },
            label = { Text(text = stringResource(R.string.mapel2)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = stringResource(R.string.syarat),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
        Divider(
            thickness = dimensionResource(R.dimen.padding_small),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateButton(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    var showDialog by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { showDialog = true },
        modifier = Modifier.size(48.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_date),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }

    LaunchedEffect(showDialog) {
        if (showDialog) {
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, day ->
                    val selectedDate = "$day/${month + 1}/$year"
                    onDateSelected(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current

    val calendar = remember { Calendar.getInstance() }
    val date = remember { mutableStateOf("") }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                date.value = "$day/${month + 1}/$year"
                onDateSelected(date.value)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }
    OutlinedButton(
        onClick = { datePickerDialog.show() },
        modifier = Modifier.size(50.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_date),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

//DONEEE

@Composable
fun TimePicker(
    selectedTime: String,
    onTimeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { showDialog = true },
        shape = CircleShape, // Set the shape to CircleShape
        modifier = modifier
    ) {
        Text(text = "Tentukan Jam")
    }

    val context = LocalContext.current

    LaunchedEffect(showDialog) {
        if (showDialog) {
            val timePickerDialog = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                    onTimeSelected(selectedTime)
                },
                0,
                0,
                true
            )

            timePickerDialog.show()
        }
    }
}
@Composable
fun TimeRangePicker(
    startTime: String,
    endTime: String,
    onStartTimeSelected: (String) -> Unit,
    onEndTimeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        TimePicker(
            selectedTime = startTime,
            onTimeSelected = { onStartTimeSelected(it) },
            modifier = Modifier.weight(1f)
        )
    }
}
