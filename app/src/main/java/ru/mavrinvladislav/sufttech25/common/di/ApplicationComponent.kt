package ru.mavrinvladislav.sufttech25.common.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.mavrinvladislav.sufttech25.common.presentation.MainActivity
import ru.mavrinvladislav.sufttech25.favourite.di.FavouriteModule
import ru.mavrinvladislav.sufttech25.search.di.SearchModule

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        PresentationModule::class,
        RemoteModule::class,
        FavouriteModule::class,
        SearchModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}