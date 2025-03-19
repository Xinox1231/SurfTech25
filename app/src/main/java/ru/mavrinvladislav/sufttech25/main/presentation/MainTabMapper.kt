package ru.mavrinvladislav.sufttech25.main.presentation

fun MainTab.toConfiguration(): DefaultMainComponent.MainConfig {
    return when (this) {
        is MainTab.Search -> {
            DefaultMainComponent.MainConfig.Search
        }

        is MainTab.Favourite -> {
            DefaultMainComponent.MainConfig.Favourite
        }
    }
}

