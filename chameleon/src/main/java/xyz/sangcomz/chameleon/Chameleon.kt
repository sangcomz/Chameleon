package xyz.sangcomz.chameleon

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by sangcomz on 12/02/2018.
 */
open class Chameleon(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private var childView: RecyclerView? = null
    override fun addView(child: View?) {
        checkValid(child)
        super.addView(child)
    }

    override fun addView(child: View?, index: Int) {
        checkValid(child)
        super.addView(child, index)
    }

    override fun addView(child: View?, width: Int, height: Int) {
        checkValid(child)
        super.addView(child, width, height)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        checkValid(child)
        super.addView(child, params)
    }

    private fun addStateView() {
        val layoutParams = ViewGroup.LayoutParams(100, 100)
        val textView = TextView(context)
        textView.text = "abcd"
        val ImageView = ImageView(context)

        super.addView(textView, layoutParams)
        super.addView(ImageView, layoutParams)
    }


    private fun checkValid(child: View?) {
        if (childCount > 0) {
            throw  IllegalStateException("Chameleon can host only one direct child")
        }
        child?.let {
            if (it !is RecyclerView)
                throw  IllegalStateException("Chameleon can only have RecyclerView as a child.")

            childView = it
        }

        addStateView()
    }

    private fun addStateTextView() {
        val stateTextView = AppCompatTextView(context)
        stateTextView.id = R.id.tv_state
        stateTextView.text = "Hallo Chameleon"
        stateTextView.ellipsize = TextUtils.TruncateAt.END
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT)
        layoutParams.topToBottom = R.id.iv_state
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID



        super.addView(stateTextView, layoutParams)
    }
}