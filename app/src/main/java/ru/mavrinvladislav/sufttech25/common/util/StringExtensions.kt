package ru.mavrinvladislav.sufttech25.common.util

fun String?.toFormattedDate(): String {
    return if (this == null) {
        "Unknown Date"
    } else {
        this.substring(0,4)
    }
}