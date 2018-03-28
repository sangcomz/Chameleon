package xyz.sangcomz.chameleon.ext

import android.content.Context

/**
 * Created by seokwon.jeong on 16/11/2017.
 */
fun Int.DP(context: Context): Float = (this * context.resources.displayMetrics.density)
fun Int.SP(context: Context): Float {
    return this * context.resources.displayMetrics.scaledDensity
}

