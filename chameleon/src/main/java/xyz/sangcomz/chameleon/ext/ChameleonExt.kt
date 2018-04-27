package xyz.sangcomz.chameleon.ext

import xyz.sangcomz.chameleon.Chameleon

fun Chameleon.setError() {
    this.showState(Chameleon.STATE.ERROR)
}

fun Chameleon.setLoading() {
    this.showState(Chameleon.STATE.LOADING)
}

fun Chameleon.setEmpty() {
    this.showState(Chameleon.STATE.EMPTY)
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
