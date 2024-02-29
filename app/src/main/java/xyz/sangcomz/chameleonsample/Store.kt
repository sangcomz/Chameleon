package xyz.sangcomz.chameleonsample

import io.reactivex.Flowable

fun getChameleons(): Flowable<List<Chameleon>> {
    return Flowable.just(arrayListOf(
            Chameleon("Liam", R.drawable.chameleon1),
            Chameleon("Noah", R.drawable.chameleon2),
            Chameleon("Elijah", R.drawable.chameleon3),
            Chameleon("Logan", R.drawable.chameleon4),
            Chameleon("Mason", R.drawable.chameleon5),
            Chameleon("Emma", R.drawable.chameleon6),
            Chameleon("Olivia", R.drawable.chameleon7),
            Chameleon("Ava", R.drawable.chameleon8)
    ))
}