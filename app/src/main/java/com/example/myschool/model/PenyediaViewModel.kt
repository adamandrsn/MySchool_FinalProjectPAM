package com.example.myschool.model

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myschool.AplikasiJadwalSiswa

// Object PenyediaViewModel digunakan untuk menyediakan factory (pabrik) untuk membuat instance ViewModel pada aplikasi
object PenyediaViewModel {
    // Properti Factory berupa viewModelFactory untuk membuat instance ViewModel dengan berbagai jenis
    val Factory = viewModelFactory {

        // Inisialisasi HomeViewModel dengan menggunakan repositori dari container aplikasi
        initializer {
            HomeViewModel(aplikasiSiswa().container.repositoriJadwalSiswa)
        }

        // Inisialisasi EntryViewModel dengan menggunakan repositori dari container aplikasi
        initializer {
            EntryViewModel(aplikasiSiswa().container.repositoriJadwalSiswa)
        }

    }
}
// Fungsi aplikasiSiswa() digunakan untuk mendapatkan instance AplikasiJadwalSiswa dari ekstensi query pada objek Application
fun CreationExtras.aplikasiSiswa(): AplikasiJadwalSiswa =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiJadwalSiswa)
