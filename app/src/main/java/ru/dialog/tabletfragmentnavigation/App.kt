package ru.dialog.tabletfragmentnavigation

import android.app.Application
import androidx.lifecycle.MutableLiveData

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        isTablet.postValue(resources.getBoolean(R.bool.tablet))
    }

    companion object {
        val isTablet = MutableLiveData(false)
    }
}