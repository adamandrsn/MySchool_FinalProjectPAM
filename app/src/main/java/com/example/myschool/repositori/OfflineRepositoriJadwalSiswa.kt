package com.example.myschool.repositori

import com.example.myschool.data.JadwalSiswa
import com.example.myschool.data.JadwalSiswaDao
import kotlinx.coroutines.flow.Flow

class OfflineRepositoriJadwalSiswa(private val jadwalsiswaDao: JadwalSiswaDao) : RepositoriJadwalSiswa {
    // Fungsi mengembalikan aliran Flow dari semua data siswa
    override fun getAllSiswaStream(): Flow<List<JadwalSiswa>> = jadwalsiswaDao.getAllSiswa()

    //Fungsi mengembalikan aliran Flow dari data siswa berdasarkan ID
    override fun getSiswaStream(id: Int): Flow<JadwalSiswa?> {
        return jadwalsiswaDao.getSiswa(id)
    }

    //Fungsi digunakan untuk menyisipkan data siswa ke dalam database
    override suspend fun insertSiswa(siswa: JadwalSiswa) = jadwalsiswaDao.insert(siswa)

    //Fungsi digunakan untuk menghapus data siswa dari database
    override suspend fun deleteSiswa(siswa: JadwalSiswa) = jadwalsiswaDao.delete(siswa)

    //Fungsi digunakan untuk memperbarui data siswa dalam database
    override suspend fun updateSiswa(siswa: JadwalSiswa) {
        jadwalsiswaDao.update(siswa)
    }
}