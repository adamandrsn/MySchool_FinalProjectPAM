package com.example.myschool.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myschool.repositori.RepositoriJadwalSiswa
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoriJadwalSiswa: RepositoriJadwalSiswa
) : ViewModel() {

    // Properti siswaUiState menggunakan mutableStateOf untuk menyimpan dan mengelola UI state pada halaman pengeditan item
    var siswaUiState by mutableStateOf(UIStateSiswa())
        private set

    // Properti itemId digunakan untuk menyimpan ID item yang diterima melalui argumen
    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    // Inisialisasi UI state pada halaman pengeditan item dengan data dari repository menggunakan coroutine viewModelScope
    init {
        viewModelScope.launch {
            // Mengambil data siswa berdasarkan ID dari repository dan mengonversinya ke UI state
            siswaUiState = repositoriJadwalSiswa.getSiswaStream(itemId)
                .filterNotNull()
                .first()
                .toUiStateSiswa(true)
        }
    }

    // Fungsi suspend updateSiswa digunakan untuk memperbarui data siswa dalam repository
    suspend fun updateSiswa() {
        // Validasi input sebelum memperbarui data siswa
        if (validasiInput(siswaUiState.detailSiswa)) {
            repositoriJadwalSiswa.updateSiswa(siswaUiState.detailSiswa.toSiswa())
        } else {
            println("Data tidak valid")
        }
    }

    // Fungsi updateUiState digunakan untuk memperbarui UI state dengan data DetailSiswa yang baru
    fun updateUiState(detailSiswa: DetailSiswa) {
        siswaUiState =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    // Fungsi validasiInput digunakan untuk memvalidasi input pada UI state
    private fun validasiInput(uiState: DetailSiswa = siswaUiState.detailSiswa): Boolean {
        return with(uiState) {
            // Memeriksa apakah semua field pada UI state tidak kosong
            tanggal.isNotBlank() && jammulai1.isNotBlank() && jamselesai1.isNotBlank() &&
                    jammulai2.isNotBlank() && jamselesai2.isNotBlank() && jurusan.isNotBlank() &&
                    mapel1.isNotBlank() && mapel2.isNotBlank()
        }
    }
}