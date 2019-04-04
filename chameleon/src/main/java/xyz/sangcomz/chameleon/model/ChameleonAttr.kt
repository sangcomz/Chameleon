package xyz.sangcomz.chameleon.model

import android.graphics.drawable.Drawable
import xyz.sangcomz.chameleon.Chameleon

/**
 * Created by seokwon.jeong on 17/11/2017.
 */
data class ChameleonAttr(var emptyText: String,
                         var emptyTextColor: Int,
                         var emptyTextSize: Float,
                         var emptyTextGravity: Int,
                         var emptySubText: String,
                         var emptySubTextColor: Int,
                         var emptySubTextSize: Float,
                         var emptySubTextGravity: Int,
                         var emptyDrawable: Drawable?,
                         var emptyButtonText: String,
                         var emptyButtonTextColor: Int,
                         var emptyButtonTextSize: Float,
                         var emptyButtonBackgroundColor: Int,
                         var useEmptyButton: Boolean,
                         var errorText: String,
                         var errorTextColor: Int,
                         var errorTextSize: Float,
                         var errorTextGravity: Int,
                         var errorSubText: String,
                         var errorSubTextColor: Int,
                         var errorSubTextSize: Float,
                         var errorSubTextGravity: Int,
                         var errorDrawable: Drawable?,
                         var errorButtonText: String,
                         var errorButtonTextColor: Int,
                         var errorButtonTextSize: Float,
                         var errorButtonBackgroundColor: Int,
                         var useErrorButton: Boolean,
                         var progressDrawable: Drawable?,
                         var useProgressBackground: Boolean,
                         var progressBackgroundColor: Int,
                         var isShowProgressWhenContentState: Boolean,
                         var isLargeProgress: Boolean,
                         var defaultChameleonState: Chameleon.STATE
)