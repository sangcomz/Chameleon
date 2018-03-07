package xyz.sangcomz.chameleon.model

import android.graphics.drawable.Drawable

/**
 * Created by seokwon.jeong on 17/11/2017.
 */
data class ChameleonAttr(var emptyText: String,
                         var emptyTextColor: Int,
                         var emptyTextSize: Float,
                         var emptyDrawable: Drawable?,
                         var errorText: String,
                         var errorTextColor: Int,
                         var errorTextSize: Float,
                         var errorDrawable: Drawable?,
                         var retryText: String,
                         var retryTextColor: Int,
                         var retryTextSize: Float,
                         var progressDrawable: Drawable,
                         var isLargeProgress: Boolean


)