package com.example.myschool.repositori

import android.content.Context
import com.example.myschool.data.DatabaseJadwalSiswa

interface ContainerApp {
    val repositoriJadwalSiswa : RepositoriJadwalSiswa
}

class ContainerDataApp(private val context: Context): ContainerApp{
    override val repositoriJadwalSiswa: RepositoriJadwalSiswa by lazy {
        OfflineRepositoriJadwalSiswa(DatabaseJadwalSiswa.getDatabase(context).JadwalSiswaDao())
    }
}