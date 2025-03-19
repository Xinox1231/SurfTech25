package ru.mavrinvladislav.sufttech25.main.presentation

fun MainChild.toTab(): MainTab {

    return when (this) {
        is MainChild.Search -> {
            MainTab.Search
        }

        is MainChild.Favourite -> {
            MainTab.Favourite
        }
    }
}