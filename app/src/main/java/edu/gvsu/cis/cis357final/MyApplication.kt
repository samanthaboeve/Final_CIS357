package edu.gvsu.cis.cis357final

import android.app.Application
import androidx.room.Room
import edu.gvsu.cis.cis357final.data.AppDatabase

class MyApplication : Application() {
    val myDB: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "fruit_database"
        ).build()
    }
}