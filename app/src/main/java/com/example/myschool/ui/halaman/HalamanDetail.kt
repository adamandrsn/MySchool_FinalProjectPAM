package com.example.myschool.ui.halaman

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.myschool.R
import com.example.myschool.data.JadwalSiswa
import com.example.myschool.navigasi.DestinasiNavigasi

object DetailsDestination : DestinasiNavigasi {
    override val route = "item_details"
    override val titleRes = R.string.detail_siswa
    const val siswaIdArg = "itemId"
    val routeWithArgs = "$route/{$siswaIdArg}"
}

@Composable
fun ItemDetails(
    siswa: JadwalSiswa, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            ItemDetailsRow(
                labelResID = R.string.tanggal,
                itemDetail = siswa.tanggal,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ItemDetailsRow(
                labelResID = R.string.jammulai1,
                itemDetail = siswa.jammulai1,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ItemDetailsRow(
                labelResID = R.string.jamselesai1,
                itemDetail = siswa.jamselesai1,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ItemDetailsRow(
                labelResID = R.string.jammulai2,
                itemDetail = siswa.jammulai2,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ItemDetailsRow(
                labelResID = R.string.jamselesai2,
                itemDetail = siswa.jamselesai2,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ItemDetailsRow(
                labelResID = R.string.jurusan,
                itemDetail = siswa.jurusan,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            ItemDetailsRow(
                labelResID = R.string.mapel1,
                itemDetail = siswa.mapel1,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )
            ItemDetailsRow(
                labelResID = R.string.mapel2,
                itemDetail = siswa.mapel2,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )
        }
    }
}

@Composable
private fun ItemDetailsRow(
    @StringRes
    labelResID: Int,
    itemDetail: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail, fontWeight = FontWeight.Bold)
    }
}

