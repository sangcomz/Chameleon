package xyz.sangcomz.chameleon

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import xyz.sangcomz.stickytimelineview.ext.DP

/**
 * Created by sangcomz on 12/02/2018.
 */
open class Chameleon(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    private var childView: RecyclerView? = null
    private var stateImageView: AppCompatImageView? = null
    private var stateTextView: AppCompatTextView? = null

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
        initStateImageViewView()
        initStateTextView()
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.addToVerticalChain(R.id.iv_state,
                ConstraintLayout.LayoutParams.PARENT_ID,
                R.id.tv_state)
        constraintSet.addToVerticalChain(R.id.tv_state,
                R.id.iv_state,
                ConstraintLayout.LayoutParams.PARENT_ID)
        constraintSet.applyTo(this)
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

    private fun initStateImageViewView() {
        val padding = 4.DP(context).toInt()
        stateImageView = AppCompatImageView(context)
        stateImageView?.apply {
            id = R.id.iv_state
            setBackgroundResource(R.drawable.drawable_progress)
            setPadding(padding, padding, padding, padding)
        }
        val background = stateImageView?.background
        if (background is AnimationDrawable) {
            background.start()
        }
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.verticalChainStyle = ConstraintLayout.LayoutParams.CHAIN_PACKED
        super.addView(stateImageView, layoutParams)
    }

    private fun initStateTextView() {
        val padding = 4.DP(context).toInt()
        stateTextView = AppCompatTextView(context)
        stateTextView?.apply {
            id = R.id.tv_state
            text = context.getString(R.string.msg_empty)
            setPadding(padding * 4, padding, padding * 4, padding)
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            TextViewCompat.setAutoSizeTextTypeWithDefaults(this,
                    TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        }
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        super.addView(stateTextView, layoutParams)
    }


}