package com.example.myschool

import android.app.Application
import com.example.myschool.repositori.ContainerApp
import com.example.myschool.repositori.ContainerDataApp

class AplikasiJadwalSiswa : Application() {
    lateinit var container: ContainerApp

    override fun onCreate() {
        super.onCreate()
        container = ContainerDataApp(this)
    }
}