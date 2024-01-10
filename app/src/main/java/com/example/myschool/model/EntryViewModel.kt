package com.example.myschool.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myschool.data.JadwalSiswa
import com.example.myschool.repositori.RepositoriJadwalSiswa

// Kelas EntryViewModel merupakan ViewModel untuk halaman entri data siswa baru
class EntryViewModel(private val repositoriJadwalSiswa: RepositoriJadwalSiswa): ViewModel() {
    // Properti uiStateSiswa menggunakan mutableStateOf untuk menyimpan dan mengelola UI state pada halaman entri data siswa
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            // Memeriksa apakah field jurusan dan mapel1 pada UI state tidak kosong
            jurusan.isNotBlank() && mapel1.isNotBlank()
        }
    }

    // Fungsi untuk memperbarui UI state dengan data DetailSiswa yang baru
    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
        println("Updated UIStateEvent: $uiStateSiswa")
    }

    // Fungsi suspend saveSiswa digunakan untuk menyimpan data siswa baru ke dalam repository jika data valid
    suspend fun saveSiswa() {
        if (validasiInput()) {
            repositoriJadwalSiswa.insertSiswa(uiStateSiswa.detailSiswa.toSiswa())
        }
    }
}

// Data class UIStateSiswa merepresentasikan UI state pada halaman entri data siswa
data class UIStateSiswa(
    // Properti detailSiswa digunakan untuk menyimpan data detail siswa pada UI
    val detailSiswa: DetailSiswa = DetailSiswa(),
    // Properti isEntryValid digunakan untuk menentukan apakah data yang di-entry valid atau tidak
    val isEntryValid: Boolean = false
)

// Data class DetailSiswa merepresentasikan data detail siswa
data class DetailSiswa(
    val id: Int = 0,
    val tanggal: String = "",
    val jammulai1: String = "",
    val jamselesai1: String = "",
    val jammulai2: String = "",
    val jamselesai2: String = "",
    val jurusan: String = "",
    val mapel1: String = "",
    val mapel2: String = "",
)

// Fungsi toSiswa() pada data class DetailSiswa digunakan untuk mengkonversi data input ke data dalam tabel JadwalSiswa
fun DetailSiswa.toSiswa(): JadwalSiswa = JadwalSiswa(
    id = id,
    tanggal = tanggal,
    jammulai1 = jammulai1,
    jamselesai1 = jamselesai1,
    jammulai2 = jammulai2,
    jamselesai2 = jamselesai2,
    jurusan = jurusan,
    mapel1 = mapel1,
    mapel2 = mapel2,
)

// Fungsi toUiStateSiswa() pada data class JadwalSiswa digunakan untuk mengkonversi data input ke data dalam data class UIStateSiswa
fun JadwalSiswa.toUiStateSiswa(isEntryValid: Boolean = false): UIStateSiswa = UIStateSiswa(
    detailSiswa = this.toDetailSiswa(),
    isEntryValid = isEntryValid
)

// Fungsi toDetailSiswa() pada data class JadwalSiswa digunakan untuk mengkonversi data input ke data dalam data class DetailSiswa
fun JadwalSiswa.toDetailSiswa(): DetailSiswa = DetailSiswa(
    id = id,
    tanggal = tanggal,
    jammulai1 = jammulai1,
    jamselesai1 = jamselesai1,
    jammulai2 = jammulai2,
    jamselesai2 = jamselesai2,
    jurusan = jurusan,
    mapel1 = mapel1,
    mapel2 = mapel2,
)