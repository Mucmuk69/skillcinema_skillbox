package com.example.test_kinopoisk.ui.auxiliaryfunctions

import android.view.View
import android.view.ViewGroup
import android.widget.TextView

//Функция для настройки правильного окончания (сезон, сезона, сезонов)
fun pluralize(count: Int, singular: String, exclusion: String, plural: String): String {
    return when {
        count % 10 == 1 && count % 100 != 11 -> {
            "$count $singular"
        }

        count % 10 in 2..4 && count % 100 !in 12..14 -> {
            "$count $exclusion"
        }

        else -> {
            "$count $plural"
        }
    }
}

//Функция для скрытия текста описания фильма
fun expandText(textView: TextView) {
    textView.maxLines = Integer.MAX_VALUE
    textView.ellipsize = null
}

//Функция для замены параметров заполнения ширины и высоты layout
fun swipeLayoutParams(element: View) {
    val layoutParams = element.layoutParams
    if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }else{
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
    }
    element.layoutParams = layoutParams
}

//Функция для скрытия и наоборот некоторых элементов разметки
fun visibilityElementXML(
    element1: View,
    element2: View,
    element3: View,
    element4: View,
) {
    element1.visibility = View.VISIBLE
    element2.visibility = View.VISIBLE
    element3.visibility = View.GONE
    element4.visibility = View.GONE
}