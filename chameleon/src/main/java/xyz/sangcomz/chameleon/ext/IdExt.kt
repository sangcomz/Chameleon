package xyz.sangcomz.chameleon.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources

/**
 * Created by seokwon.jeong on 16/11/2017.
 */
fun Int.getDrawable(context: Context): Drawable? {
    return AppCompatResources.getDrawable(context, this)
}

fun Int.getColor(context: Context): Int {
    return ContextCompat.getColor(context, this)
}