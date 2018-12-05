package xyz.sangcomz.chameleon.model

import android.view.View

data class ButtonSettingBundle(val text: String? = null,
                               val textSize: Float? = null,
                               val textColor: Int? = null,
                               val backgroundColor: Int? = null,
                               val listener: ((View) -> Unit)? = null)