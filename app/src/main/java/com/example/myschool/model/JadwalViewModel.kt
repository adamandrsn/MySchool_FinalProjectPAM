package com.example.myschool.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myschool.data.JadwalSiswa
import com.example.myschool.repositori.RepositoriJadwalSiswa
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class JadwalViewModel(private val repositoriJadwalSiswa: RepositoriJadwalSiswa) : ViewModel() {

    // Properti TIMEOUT_MILLIS digunakan untuk menentukan waktu timeout pada stateIn
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    // Properti homeUiState merupakan StateFlow yang akan digunakan untuk mengelola UI state pada halaman utama
    val jadwalUiState: StateFlow<JadwalUiState> = repositoriJadwalSiswa.getAllSiswaStream()
        .filterNotNull()
        .map { JadwalUiState(listSiswa = it.toList()) }
        .stateIn(
            // Menyimpan state UI menggunakan stateIn dengan SharingStarted.WhileSubscribed dan timeout 5 detik
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = JadwalUiState()
        )
}

// Data class HomeUiState merepresentasikan UI state pada halaman utama
data class JadwalUiState(
    // Properti listSiswa digunakan untuk menyimpan daftar data siswa yang akan ditampilkan pada UI
    val listSiswa: List<JadwalSiswa> = listOf()
)