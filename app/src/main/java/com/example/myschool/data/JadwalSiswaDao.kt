package com.example.myschool.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// Mendeklarasikan entitas Siswa
@Dao
interface JadwalSiswaDao {
    // Fungsi menambahkan data Jadwal Siswa ke database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(siswa: JadwalSiswa)

    // Fungsi memperbarui data Jadwal Siswa dalam database
    @Update
    suspend fun update(siswa: JadwalSiswa)

    // Fungsi menghapus data Jadwal Siswa dari database
    @Delete
    suspend fun delete(siswa: JadwalSiswa)

    // Fungsi mengambil data Jadwal Siswa berdasarkan ID menggunakan Flow
    @Query("SELECT * from tblJadwal WHERE id = :id")
    fun getSiswa(id: Int): Flow<JadwalSiswa>

    // Fungsi mengambil semua data Jadwal Siswa dari database dan mengurutkannya berdasarkan tanggal secara terkecil-terbesar menggunakan Flow
    @Query("SELECT * from tblJadwal ORDER BY tanggal ASC")
    fun getAllSiswa(): Flow<List<JadwalSiswa>>
}
