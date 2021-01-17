package com.example.moviecatalogue.core.utils

import android.graphics.Paint
import android.view.View
import android.widget.TextView

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

inline var TextView.underline: Boolean
    set(visible) {
        paintFlags = if (visible) paintFlags or Paint.UNDERLINE_TEXT_FLAG
        else paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
    }
    get() = paintFlags and Paint.UNDERLINE_TEXT_FLAG == Paint.UNDERLINE_TEXT_FLAG