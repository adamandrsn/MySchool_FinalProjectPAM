package com.example.myschool.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myschool.repositori.RepositoriJadwalSiswa
import com.example.myschool.ui.halaman.DetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// Kelas DetailsViewModel merupakan ViewModel untuk halaman detail item
class DetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriJadwalSiswa: RepositoriJadwalSiswa
) : ViewModel() {

    // Properti siswaId digunakan untuk menyimpan ID siswa yang diterima melalui argumen
    private val siswaId: Int = checkNotNull(savedStateHandle[DetailsDestination.siswaIdArg])
    // Properti uiState merupakan StateFlow yang akan digunakan untuk mengelola UI state pada halaman detail
    val uiState: StateFlow<ItemDetailsUiState> =
        // Menggunakan flow dari RepositoriJadwalSiswa untuk mendapatkan data siswa berdasarkan ID
        repositoriJadwalSiswa.getSiswaStream(siswaId)
            .filterNotNull()
            .map {
                // Mapping data siswa ke ItemDetailsUiState
                ItemDetailsUiState(detailSiswa = it.toDetailSiswa())
            }.stateIn(
                // Menyimpan state UI menggunakan stateIn dengan SharingStarted.WhileSubscribed dan timeout 5 detik
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ItemDetailsUiState()
            )

    // Fungsi suspend deleteItem digunakan untuk menghapus data siswa dari repositori
    suspend fun deleteItem() {
        repositoriJadwalSiswa.deleteSiswa(uiState.value.detailSiswa.toSiswa())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    //detailSiswa digunakan untuk menyimpan data detail siswa pada UI
    val detailSiswa: DetailSiswa = DetailSiswa(),
)


