package xyz.sangcomz.chameleon.model

import android.graphics.drawable.Drawable

/**
 * Created by seokwon.jeong on 17/11/2017.
 */
data class ChameleonAttr(var emptyText: String,
                         var emptyTextColor: Int,
                         var emptyTextSize: Float,
                         var emptySubText: String,
                         var emptySubTextColor: Int,
                         var emptySubTextSize: Float,
                         var emptyDrawable: Drawable?,
                         var emptyButtonText: String,
                         var emptyButtonTextColor: Int,
                         var emptyButtonTextSize: Float,
                         var emptyButtonBackgroundColor: Int,
                         var useEmptyButton:Boolean,
                         var errorText: String,
                         var errorTextColor: Int,
                         var errorTextSize: Float,
                         var errorSubText: String,
                         var errorSubTextColor: Int,
                         var errorSubTextSize: Float,
                         var errorDrawable: Drawable?,
                         var errorButtonText: String,
                         var errorButtonTextColor: Int,
                         var errorButtonTextSize: Float,
                         var errorButtonBackgroundColor: Int,
                         var useErrorButton:Boolean,
                         var progressDrawable: Drawable?,
                         var isLargeProgress: Boolean


)