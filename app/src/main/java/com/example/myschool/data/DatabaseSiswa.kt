package com.example.myschool.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [JadwalSiswa::class], version = 1, exportSchema = false)
abstract class DatabaseJadwalSiswa : RoomDatabase() {
    abstract fun JadwalSiswaDao(): JadwalSiswaDao
    companion object {
        @Volatile
        private var Instance: DatabaseJadwalSiswa? = null
        fun getDatabase(context: Context): DatabaseJadwalSiswa {
            // Menggunakan if-null untuk memastikan bahwa instance hanya dibuat sekali secara aman dan thread-safe
            return (Instance ?: synchronized(this) {
                // Membangun database menggunakan Room dengan nama "jadwalsiswa_database"
                Room.databaseBuilder(
                    context,
                    DatabaseJadwalSiswa::class.java,
                    "jadwalsiswa_database"
                )
                    .build().also { Instance = it }
            })
        }
    }
}