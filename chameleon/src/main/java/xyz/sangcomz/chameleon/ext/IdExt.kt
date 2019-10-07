package xyz.sangcomz.chameleon.ext

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.appcompat.content.res.AppCompatResources

/**
 * Created by seokwon.jeong on 16/11/2017.
 */
fun Int.getDrawable(context: Context): Drawable? {
    return AppCompatResources.getDrawable(context, this)
}

fun Int.getColor(context: Context): Int {
    return ContextCompat.getColor(context, this)
}