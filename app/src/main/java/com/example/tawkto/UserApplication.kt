package com.example.tawkto

import android.Manifest.permission.INTERNET
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.example.tawkto.data.api.RetrofitInst
import com.example.tawkto.data.db.UserDatabase
import com.example.tawkto.data.repository.UsersRepository


class UserApplication: Application() {
    lateinit var usersRepository: UsersRepository

    override fun onCreate() {
        super.onCreate()
        initialize()

    }

    private fun initialize() {
        val apiUsers = RetrofitInst.apiUsers
        val database = UserDatabase.getDatabase(applicationContext)
        usersRepository = UsersRepository(apiUsers, database, applicationContext)
    }

    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

}