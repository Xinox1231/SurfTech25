package ru.mavrinvladislav.sufttech25.common.presentation

import android.app.Application
import ru.mavrinvladislav.sufttech25.common.di.DaggerApplicationComponent

class BookApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}