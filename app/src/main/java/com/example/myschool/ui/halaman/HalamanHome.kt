package com.example.myschool.ui.halaman

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myschool.R
import com.example.myschool.data.JadwalSiswa
import com.example.myschool.model.HomeViewModel
import com.example.myschool.model.PenyediaViewModel
import com.example.myschool.navigasi.DestinasiNavigasi
import com.example.myschool.navigasi.SiswaTopAppBar
import kotlin.random.Random

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.entry_siswa)
                )
            }
        },
    ) { innerPadding ->
        val uiStateSiswa by viewModel.homeUiState.collectAsState()
        BodyHome(
            itemSiswa = uiStateSiswa.listSiswa,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onSiswaClick = onDetailClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyHome(
    itemSiswa: List<JadwalSiswa>,
    modifier: Modifier = Modifier,
    onSiswaClick: (Int) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var isSortDescending by remember { mutableStateOf(true) } // Menyimpan status urutan saat ini
    val filteredSiswa = itemSiswa.filter {
        it.tanggal.contains(searchText, ignoreCase = true) ||
                it.jurusan.contains(searchText, ignoreCase = true) ||
                it.jammulai1.contains(searchText, ignoreCase = true) ||
                it.jammulai2.contains(searchText, ignoreCase = true) ||
                it.mapel1.contains(searchText, ignoreCase = true) ||
                it.mapel2.contains(searchText, ignoreCase = true)
    }.let { if (isSortDescending) it.sortedByDescending { it.tanggal } else it.sortedBy { it.tanggal } }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = {
                Text(text = stringResource(id = R.string.search))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                .padding(bottom = dimensionResource(id = R.dimen.padding_small))
        )
        // Tombol untuk mengubah urutan data
        Button(
            onClick = { isSortDescending = !isSortDescending },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(text = if (isSortDescending) "Urut dari tanggal terdekat" else "Urut dari tanggal terjauh")
        }
        if (filteredSiswa.isEmpty() && searchText.isNotEmpty()) {
            Text(
                text = stringResource(id = androidx.compose.ui.R.string.not_selected),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.padding_small))
            )
        } else if (filteredSiswa.isNotEmpty()){
            ListSiswa(
                itemSiswa =  filteredSiswa,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                onItemClick = { onSiswaClick(it.id) }
            )
        }
    }
}

@Composable
fun ListSiswa(
    itemSiswa: List<JadwalSiswa>,
    modifier: Modifier = Modifier,
    onItemClick: (JadwalSiswa) -> Unit
) {
    LazyColumn(modifier = Modifier) {
        items(items = itemSiswa, key = { it.id }) { person ->
            DataSiswa(
                siswa = person,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(person) }
            )
        }
    }
}

@Composable
fun DataSiswa(
    siswa: JadwalSiswa,
    modifier: Modifier = Modifier
) {
    val randomColor = getRandomColor()

    Card(
        modifier = modifier
            .background(randomColor)
            .padding(dimensionResource(id = R.dimen.padding_small))
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = dimensionResource(id = R.dimen.padding_small))
                )
                Text(
                    text = siswa.tanggal,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
                )
                Spacer(Modifier.weight(2f))
                Text(
                    text = siswa.jurusan,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = siswa.jammulai1,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "-")
                Text(
                    text = siswa.jamselesai1,//selesai1
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = siswa.mapel1,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = siswa.jammulai2,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "-")
                Text(
                    text = siswa.jamselesai2,//harusnya selesai2
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = siswa.mapel2,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

private fun getRandomColor(): Color {
    val random = Random.Default
    return Color(random.nextFloat(), random.nextFloat(), random.nextFloat())
}

//DONE