package mk.ukim.finki.mpip.housing_service.service

import mk.ukim.finki.mpip.housing_service.MyApplication

class LocalStorageService {

    private val sharedPreferences = MyApplication
        .applicationContext()
        .getSharedPreferences("housing_service", 0)
    private val editor = sharedPreferences.edit()

    fun saveData(key: String, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String, value: String?) = sharedPreferences.getString(key, value)
}