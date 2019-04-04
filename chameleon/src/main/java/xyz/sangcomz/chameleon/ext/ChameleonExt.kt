package xyz.sangcomz.chameleon.ext

import android.graphics.drawable.Drawable
import xyz.sangcomz.chameleon.Chameleon
import xyz.sangcomz.chameleon.model.TextSettingBundle

fun Chameleon.setError(drawable: Drawable? = null,
                       titleTextSettingBundle: TextSettingBundle = TextSettingBundle(),
                       subTextSettingBundle: TextSettingBundle = TextSettingBundle()) {
    this.showState(Chameleon.STATE.ERROR,
            drawable,
            titleTextSettingBundle,
            subTextSettingBundle)
}

fun Chameleon.setLoading() {
    this.showState(Chameleon.STATE.LOADING)
}

fun Chameleon.setEmpty(drawable: Drawable? = null,
                       titleTextSettingBundle: TextSettingBundle = TextSettingBundle(),
                       subTextSettingBundle: TextSettingBundle = TextSettingBundle()) {
    this.showState(Chameleon.STATE.EMPTY,
            drawable,
            titleTextSettingBundle,
            subTextSettingBundle)
}

fun Chameleon.setContent() {
    this.showState(Chameleon.STATE.CONTENT)
}

fun Chameleon?.setErrorIfEmpty() {
    if (this != null) {
        if (this.hasNoContent()) {
            this.setError()
        }
    }
}

fun Chameleon?.contentOrEmpty(hasContent: Boolean) {
    if (hasContent) this?.setContent() else this?.setEmpty()
}

fun Chameleon?.loadingOrContent(isLoading: Boolean) {
    if (isLoading) this?.setLoading() else this?.setContent()
}
