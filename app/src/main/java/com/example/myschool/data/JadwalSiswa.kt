package com.example.myschool.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblJadwal")
data class JadwalSiswa(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val tanggal : String,
    val jammulai1 : String,
    val jamselesai1 : String,
    val jammulai2 : String,
    val jamselesai2 : String,
    val jurusan : String,
    val mapel1 : String,
    val mapel2 : String
)