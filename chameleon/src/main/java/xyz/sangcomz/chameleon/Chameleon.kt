package xyz.sangcomz.chameleon

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import xyz.sangcomz.chameleon.ext.DP
import xyz.sangcomz.chameleon.ext.getDrawable
import xyz.sangcomz.chameleon.model.ChameleonAttr

/**
 * Created by sangcomz on 12/02/2018.
 */
open class Chameleon(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    enum class STATE {
        LOADING,
        ERROR,
        EMPTY,
        CONTENT
    }

    private var stateContentView: RecyclerView? = null
    private var stateImageView: AppCompatImageView? = null
    private var stateTextView: AppCompatTextView? = null
    private var stateProgressBar: ProgressBar? = null
    private var stateRetryButton: AppCompatButton? = null

    private var chameleonAttr: ChameleonAttr? = null

    init {
        attrs?.let {
            val a = context?.theme?.obtainStyledAttributes(
                    attrs,
                    R.styleable.Chameleon,
                    0, 0)

            a?.let {
                chameleonAttr =
                        ChameleonAttr(
                                it.getString(R.styleable.Chameleon_emptyText) ?: "empty",
                                it.getColor(R.styleable.Chameleon_emptyTextColor, ContextCompat.getColor(context, R.color.colorDefaultText)),
                                it.getDimension(R.styleable.Chameleon_emptyTextSize, context.resources.getDimension(R.dimen.default_text_size)),
                                it.getDrawable(R.styleable.Chameleon_emptyDrawable)
                                        ?: R.drawable.ic_empty.getDrawable(context),
                                it.getString(R.styleable.Chameleon_errorText) ?: "error",
                                it.getColor(R.styleable.Chameleon_errorTextColor, ContextCompat.getColor(context, R.color.colorDefaultText)),
                                it.getDimension(R.styleable.Chameleon_errorTextSize, context.resources.getDimension(R.dimen.default_text_size)),
                                it.getDrawable(R.styleable.Chameleon_errorDrawable)
                                        ?: R.drawable.ic_error.getDrawable(context),
                                it.getString(R.styleable.Chameleon_retryText) ?: "retry",
                                it.getColor(R.styleable.Chameleon_retryTextColor, ContextCompat.getColor(context, R.color.colorDefaultText)),
                                it.getDimension(R.styleable.Chameleon_retryTextSize, context.resources.getDimension(R.dimen.default_text_size)),
                                it.getDrawable(R.styleable.Chameleon_progressDrawable),
                                it.getBoolean(R.styleable.Chameleon_isLargeProgress, false)
                        )
            }

        }
    }

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
        chameleonAttr?.let {
            initStateImageViewView()
            initStateTextView()
            initStateRetryButton(it)
            initStateProgressBar(it)
        }
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        constraintSet.addToVerticalChain(R.id.iv_state,
                ConstraintLayout.LayoutParams.PARENT_ID,
                R.id.tv_state)

        constraintSet.addToVerticalChain(R.id.tv_state,
                R.id.iv_state,
                R.id.bt_state)

        constraintSet.addToVerticalChain(R.id.bt_state,
                R.id.tv_state,
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

            stateContentView = it
        }

        addStateView()
    }

    private fun initStateImageViewView() {
        val padding = 4.DP(context).toInt()
        stateImageView = AppCompatImageView(context)
        stateImageView?.apply {
            id = R.id.iv_state
            setPadding(padding, padding, padding, padding)
            visibility = View.GONE
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
            setPadding(padding * 4, padding, padding * 4, padding)
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            textSize = context.resources.getDimension(R.dimen.default_text_size)
            TextViewCompat.setAutoSizeTextTypeWithDefaults(this,
                    TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
            visibility = View.GONE
        }
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        super.addView(stateTextView, layoutParams)
    }

    private fun initStateProgressBar(attr: ChameleonAttr) {
        stateProgressBar =
                if (attr.isLargeProgress)
                    ProgressBar(context,
                            null,
                            android.R.attr.progressBarStyleLarge)
                else
                    ProgressBar(context)

        stateProgressBar?.apply {
            id = R.id.pb_state
            chameleonAttr?.progressDrawable?.let {
                indeterminateDrawable = it
            }
            visibility = View.GONE
        }
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        super.addView(stateProgressBar, layoutParams)
    }


    private fun initStateRetryButton(attr: ChameleonAttr) {
        stateRetryButton = AppCompatButton(context)

        stateRetryButton?.apply {
            id = R.id.bt_state
            text = attr.retryText
            setTextColor(attr.retryTextColor)
            textSize = attr.retryTextSize
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            TextViewCompat.setAutoSizeTextTypeWithDefaults(this,
                    TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
            visibility = View.GONE
        }
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        super.addView(stateRetryButton, layoutParams)
    }

    fun showState(state: STATE) {
        when (state) {
            STATE.CONTENT -> {
                stateContentView?.visibility = View.VISIBLE
                stateImageView?.visibility = View.GONE
                stateTextView?.visibility = View.GONE
                stateProgressBar?.visibility = View.GONE
                stateRetryButton?.visibility = View.GONE
            }
            STATE.ERROR -> {
                stateContentView?.visibility = View.GONE
                stateImageView?.visibility = View.VISIBLE
                stateTextView?.visibility = View.VISIBLE
                stateProgressBar?.visibility = View.GONE
                stateRetryButton?.visibility = View.VISIBLE
            }
            STATE.LOADING -> {
                stateContentView?.visibility = View.GONE
                stateImageView?.visibility = View.GONE
                stateTextView?.visibility = View.GONE
                stateProgressBar?.visibility = View.VISIBLE
                stateRetryButton?.visibility = View.GONE
            }
            STATE.EMPTY -> {
                stateContentView?.visibility = View.GONE
                stateImageView?.visibility = View.VISIBLE
                stateTextView?.visibility = View.VISIBLE
                stateProgressBar?.visibility = View.GONE
                stateRetryButton?.visibility = View.GONE
            }
        }
    }

    private fun setStateImageView() {
        stateImageView?.setImageDrawable(chameleonAttr?.emptyDrawable)
    }
}