package com.example.myschool.repositori

import com.example.myschool.data.JadwalSiswa
import kotlinx.coroutines.flow.Flow
// Antarmuka (interface) RepositoriJadwalSiswa mendefinisikan operasi-operasi yang dapat dilakukan terhadap entitas JadwalSiswa di database
interface RepositoriJadwalSiswa {
    // Fungsi mengambil semua data Siswa secara stream menggunakan Flow
    fun getAllSiswaStream(): Flow<List<JadwalSiswa>>

    // Fungsi mengambil data Siswa berdasarkan ID
    fun getSiswaStream(id: Int): Flow<JadwalSiswa?>

    // Fungsi menambahkan data Siswa
    suspend fun insertSiswa(siswa: JadwalSiswa)

    // Fungsi menghapus data Siswa
    suspend fun deleteSiswa(siswa: JadwalSiswa)

    // Fungsi memperbarui data Siswa
    suspend fun updateSiswa(siswa: JadwalSiswa)
}