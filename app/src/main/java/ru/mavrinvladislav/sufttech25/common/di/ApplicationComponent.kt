package ru.mavrinvladislav.sufttech25.common.di

import dagger.Component
import ru.mavrinvladislav.sufttech25.common.presentation.MainActivity
import ru.mavrinvladislav.sufttech25.search.di.SearchModule

@ApplicationScope
@Component(
    modules = [
        RemoteModule::class,
        SearchModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)
}