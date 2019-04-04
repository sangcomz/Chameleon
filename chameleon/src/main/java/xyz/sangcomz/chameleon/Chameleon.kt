package xyz.sangcomz.chameleon

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.ConstraintSet.BOTTOM
import android.support.constraint.ConstraintSet.END
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.constraint.ConstraintSet.START
import android.support.constraint.ConstraintSet.TOP
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import xyz.sangcomz.chameleon.ext.DP
import xyz.sangcomz.chameleon.ext.getDrawable
import xyz.sangcomz.chameleon.model.ButtonSettingBundle
import xyz.sangcomz.chameleon.model.ChameleonAttr
import xyz.sangcomz.chameleon.model.TextSettingBundle

/**
 * Created by sangcomz on 12/02/2018.
 */
open class Chameleon(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    enum class STATE {
        LOADING,
        ERROR,
        EMPTY,
        NONE,
        CONTENT
    }

    private var stateContentView: View? = null
    private var stateImageView: AppCompatImageView? = null
    private var stateTitleTextView: AppCompatTextView? = null
    private var stateSubTextView: AppCompatTextView? = null
    private var stateProgressLayout: FrameLayout? = null
    //    private var stateProgressBar: ProgressBar? = null
    private var stateButton: AppCompatButton? = null
    private var errorButtonListener: ((View) -> Unit)? = null
    private var emptyButtonListener: ((View) -> Unit)? = null
    private var stateChangeListener: ((newState: STATE, oldState: STATE) -> Unit)? = null

    private var chameleonAttr: ChameleonAttr? = null
    private var currentState: STATE = STATE.EMPTY

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
                                it.getColor(R.styleable.Chameleon_emptyTextColor, ContextCompat.getColor(context, R.color.colorTitleText)),
                                it.getDimension(R.styleable.Chameleon_emptyTextSize, context.resources.getDimension(R.dimen.title_text_size)),
                                it.getInt(R.styleable.Chameleon_emptyTextGravity, 0),
                                it.getString(R.styleable.Chameleon_emptySubText) ?: "empty content",
                                it.getColor(R.styleable.Chameleon_emptySubTextColor, ContextCompat.getColor(context, R.color.colorSubText)),
                                it.getDimension(R.styleable.Chameleon_emptySubTextSize, context.resources.getDimension(R.dimen.sub_text_size)),
                                it.getInt(R.styleable.Chameleon_emptySubTextGravity, 0),
                                it.getResourceId(R.styleable.Chameleon_emptyDrawable, R.drawable.ic_chameleon_empty).getDrawable(context),
                                it.getString(R.styleable.Chameleon_emptyButtonText) ?: "retry",
                                it.getColor(R.styleable.Chameleon_emptyButtonTextColor, ContextCompat.getColor(context, R.color.colorTitleText)),
                                it.getDimension(R.styleable.Chameleon_emptyButtonTextSize, context.resources.getDimension(R.dimen.title_text_size)),
                                it.getColor(R.styleable.Chameleon_emptyButtonBackgroundColor, ContextCompat.getColor(context, R.color.colorSubText)),
                                it.getBoolean(R.styleable.Chameleon_useEmptyButton, false),
                                it.getString(R.styleable.Chameleon_errorText) ?: "error",
                                it.getColor(R.styleable.Chameleon_errorTextColor, ContextCompat.getColor(context, R.color.colorTitleText)),
                                it.getDimension(R.styleable.Chameleon_errorTextSize, context.resources.getDimension(R.dimen.title_text_size)),
                                it.getInt(R.styleable.Chameleon_errorTextGravity, 0),
                                it.getString(R.styleable.Chameleon_errorSubText) ?: "error content",
                                it.getColor(R.styleable.Chameleon_errorSubTextColor, ContextCompat.getColor(context, R.color.colorSubText)),
                                it.getDimension(R.styleable.Chameleon_errorSubTextSize, context.resources.getDimension(R.dimen.sub_text_size)),
                                it.getInt(R.styleable.Chameleon_errorSubTextGravity, 0),
                                it.getResourceId(R.styleable.Chameleon_errorDrawable, R.drawable.ic_chameleon_error).getDrawable(context),
                                it.getString(R.styleable.Chameleon_errorButtonText) ?: "retry",
                                it.getColor(R.styleable.Chameleon_errorButtonTextColor, ContextCompat.getColor(context, R.color.colorTitleText)),
                                it.getDimension(R.styleable.Chameleon_errorButtonTextSize, context.resources.getDimension(R.dimen.title_text_size)),
                                it.getColor(R.styleable.Chameleon_errorButtonBackgroundColor, ContextCompat.getColor(context, R.color.colorTitleText)),
                                it.getBoolean(R.styleable.Chameleon_useErrorButton, false),
                                it.getDrawable(R.styleable.Chameleon_progressDrawable),
                                it.getBoolean(R.styleable.Chameleon_useProgressBackground, false),
                                it.getColor(R.styleable.Chameleon_progressBackgroundColor, ContextCompat.getColor(context, R.color.colorLoadingBackground)),
                                it.getBoolean(R.styleable.Chameleon_isShowContentWhenLoadingState, false),
                                it.getBoolean(R.styleable.Chameleon_isLargeProgress, false),
                                stateFromInt(it.getInt(R.styleable.Chameleon_defaultChameleonState, -1))
                        )
            }

        }
    }

    /**
     * Get the matching state from the view attribute value
     * If the `defaultState` is not set by the developer,
     * then by default `STATE.CONTENT` will be set
     *
     * @param int - Value from the attribute
     * @return State to set
     */
    private fun stateFromInt(int: Int): STATE {
        return when (int) {
            1 -> STATE.LOADING
            2 -> STATE.ERROR
            3 -> STATE.EMPTY
            4 -> STATE.NONE
            else -> STATE.CONTENT
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
            initStateTitleTextView()
            initStateSubTextView()
            initStateButton(it)
            initStateProgressBar(it)

            showState(it.defaultChameleonState)
        }
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        constraintSet.connect(R.id.iv_state, TOP, PARENT_ID, TOP)
        constraintSet.connect(R.id.iv_state, START, PARENT_ID, START)
        constraintSet.connect(R.id.iv_state, BOTTOM, R.id.tv_title_state, TOP, 4.DP(context).toInt())
        constraintSet.connect(R.id.iv_state, END, PARENT_ID, END)
        constraintSet.setVerticalBias(R.id.iv_state, 1f)

        constraintSet.connect(R.id.tv_title_state, TOP, PARENT_ID, TOP)
        constraintSet.connect(R.id.tv_title_state, START, PARENT_ID, START)
        constraintSet.connect(R.id.tv_title_state, BOTTOM, PARENT_ID, BOTTOM)
        constraintSet.connect(R.id.tv_title_state, END, PARENT_ID, END)

        constraintSet.connect(R.id.tv_sub_state, TOP, R.id.tv_title_state, BOTTOM, 4.DP(context).toInt())
        constraintSet.connect(R.id.tv_sub_state, START, PARENT_ID, START)
        constraintSet.connect(R.id.tv_sub_state, END, PARENT_ID, END)

        constraintSet.connect(R.id.bt_state, TOP, R.id.tv_sub_state, BOTTOM, 16.DP(context).toInt())
        constraintSet.connect(R.id.bt_state, START, PARENT_ID, START)
        constraintSet.connect(R.id.bt_state, BOTTOM, PARENT_ID, BOTTOM)
        constraintSet.connect(R.id.bt_state, END, PARENT_ID, END)
        constraintSet.setVerticalBias(R.id.bt_state, 0f)

        constraintSet.applyTo(this)

    }


    private fun checkValid(child: View?) {
        if (childCount > 0) {
            throw  IllegalStateException("Chameleon can host only one direct child")
        }
        child?.let {
            stateContentView = it
        }

        addStateView()
    }

    private fun initStateImageViewView() {
        stateImageView = AppCompatImageView(context).apply {
            id = R.id.iv_state
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

    private fun initStateTitleTextView() {
        val padding = 32.DP(context).toInt()
        stateTitleTextView = AppCompatTextView(context)
        stateTitleTextView?.apply {
            id = R.id.tv_title_state
            setPadding(padding, 0, padding, 0)
            ellipsize = TextUtils.TruncateAt.END
            visibility = View.GONE
        }
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        super.addView(stateTitleTextView, layoutParams)
    }

    private fun initStateSubTextView() {
        val padding = 32.DP(context).toInt()
        stateSubTextView = AppCompatTextView(context)
        stateSubTextView?.apply {
            id = R.id.tv_sub_state
            setPadding(padding, 0, padding, 0)
            ellipsize = TextUtils.TruncateAt.END
            visibility = View.GONE
        }
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        super.addView(stateSubTextView, layoutParams)
    }

    private fun initStateProgressBar(attr: ChameleonAttr) {
        stateProgressLayout = FrameLayout(context)
        stateProgressLayout?.apply {
            id = R.id.pb_state
            if (attr.useProgressBackground)
                setBackgroundColor(attr.progressBackgroundColor)
//            setBackgroundColor(Color.TRANSPARENT)
            visibility = View.GONE
        }
        val stateProgressBar =
                if (attr.isLargeProgress)
                    ProgressBar(context,
                            null,
                            android.R.attr.progressBarStyleLarge)
                else
                    ProgressBar(context)

        stateProgressBar.apply {
            //            id = R.id.pb_state
            chameleonAttr?.progressDrawable?.let {
                indeterminateDrawable = it
            }
        }
        val progressBarLayoutParams = FrameLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        progressBarLayoutParams.gravity = CENTER

        stateProgressLayout?.addView(stateProgressBar, progressBarLayoutParams)
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT)
        super.addView(stateProgressLayout, layoutParams)
    }


    private fun initStateButton(attr: ChameleonAttr) {
        stateButton = AppCompatButton(context)

        stateButton?.apply {
            id = R.id.bt_state
            text = attr.errorButtonText
            setTextColor(attr.errorButtonTextColor)
            textSize = attr.errorButtonTextSize
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            visibility = View.GONE
        }
        val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        super.addView(stateButton, layoutParams)
    }

    fun showState(state: STATE,
                  customDrawable: Drawable? = null,
                  titleTextSettingBundle: TextSettingBundle = TextSettingBundle(),
                  subTextSettingBundle: TextSettingBundle = TextSettingBundle(),
                  buttonSettingBundle: ButtonSettingBundle = ButtonSettingBundle()) {
        when (state) {
            STATE.CONTENT -> {
                setViewVisibility(View.VISIBLE)
            }
            STATE.ERROR -> {
                chameleonAttr?.let {
                    val errorDrawable = customDrawable ?: it.errorDrawable
                    setStateImageView(errorDrawable
                            ?: R.drawable.ic_chameleon_error.getDrawable(context))

                    setStateTitleTextView(titleTextSettingBundle.text ?: it.errorText,
                            titleTextSettingBundle.textSize ?: it.errorTextSize,
                            titleTextSettingBundle.textColor ?: it.errorTextColor,
                            titleTextSettingBundle.textGravity ?: it.errorTextGravity)

                    setStateSubTextView(subTextSettingBundle.text ?: it.errorSubText,
                            subTextSettingBundle.textSize ?: it.errorSubTextSize,
                            subTextSettingBundle.textColor ?: it.errorSubTextColor,
                            subTextSettingBundle.textGravity ?: it.errorSubTextGravity)

                    if (it.useErrorButton)
                        setStateButton(buttonSettingBundle.text ?: it.errorButtonText,
                                buttonSettingBundle.textSize ?: it.errorButtonTextSize,
                                buttonSettingBundle.textColor ?: it.errorButtonTextColor,
                                buttonSettingBundle.backgroundColor
                                        ?: it.errorButtonBackgroundColor,
                                buttonSettingBundle.listener ?: errorButtonListener)

                    setViewVisibility(imageViewVisible = View.VISIBLE,
                            titleViewVisible = View.VISIBLE,
                            subViewVisible = View.VISIBLE,
                            retryViewVisible = if (it.useEmptyButton) View.VISIBLE else View.GONE)
                }
            }
            STATE.LOADING -> {
                chameleonAttr?.let {
                    setViewVisibility(progressViewVisible = View.VISIBLE,
                            contentViewVisible = if (it.isShowProgressWhenContentState
                                    && (currentState == STATE.CONTENT))
                                View.VISIBLE
                            else
                                View.GONE)
                }
            }
            STATE.EMPTY -> {
                chameleonAttr?.let {
                    val emptyDrawable = customDrawable ?: it.emptyDrawable
                    setStateImageView(emptyDrawable
                            ?: R.drawable.ic_chameleon_empty.getDrawable(context))

                    setStateTitleTextView(titleTextSettingBundle.text ?: it.emptyText,
                            titleTextSettingBundle.textSize ?: it.emptyTextSize,
                            titleTextSettingBundle.textColor ?: it.emptyTextColor,
                            titleTextSettingBundle.textGravity ?: it.emptyTextGravity)

                    setStateSubTextView(subTextSettingBundle.text ?: it.emptySubText,
                            subTextSettingBundle.textSize ?: it.emptySubTextSize,
                            subTextSettingBundle.textColor ?: it.emptySubTextColor,
                            subTextSettingBundle.textGravity ?: it.emptySubTextGravity)

                    if (it.useEmptyButton)
                        setStateButton(buttonSettingBundle.text ?: it.emptyButtonText,
                                buttonSettingBundle.textSize ?: it.emptyButtonTextSize,
                                buttonSettingBundle.textColor ?: it.emptyButtonTextColor,
                                buttonSettingBundle.backgroundColor
                                        ?: it.emptyButtonBackgroundColor,
                                buttonSettingBundle.listener ?: emptyButtonListener)

                    setViewVisibility(imageViewVisible = View.VISIBLE,
                            titleViewVisible = View.VISIBLE,
                            subViewVisible = View.VISIBLE,
                            retryViewVisible = if (it.useEmptyButton) View.VISIBLE else View.GONE)
                }
            }
            STATE.NONE -> setViewVisibility()
        }

        if (state != currentState) {
            stateChangeListener?.invoke(state, currentState)
        }
        currentState = state
    }

    fun getState(): STATE = currentState

    fun hasNoContent(): Boolean {
        return (stateContentView as? RecyclerView)?.adapter?.itemCount == 0
    }

    private fun setViewVisibility(contentViewVisible: Int = View.GONE,
                                  imageViewVisible: Int = View.GONE,
                                  titleViewVisible: Int = View.GONE,
                                  subViewVisible: Int = View.GONE,
                                  progressViewVisible: Int = View.GONE,
                                  retryViewVisible: Int = View.GONE) {
        stateContentView?.visibility = contentViewVisible
        stateImageView?.visibility = imageViewVisible
        stateTitleTextView?.visibility = titleViewVisible
        stateSubTextView?.visibility = subViewVisible

        stateProgressLayout?.visibility = progressViewVisible
        if (progressViewVisible == View.VISIBLE) stateProgressLayout?.bringToFront()

        stateButton?.visibility = retryViewVisible
    }

    private fun setStateImageView(drawable: Drawable?) {
        stateImageView?.setImageDrawable(drawable)
    }

    private fun setStateTitleTextView(content: String, size: Float, color: Int, gravity: Int) {
        stateTitleTextView?.apply {
            this.gravity = gravity
            text = content
            setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
            setTextColor(color)
        }
    }

    private fun setStateSubTextView(content: String, size: Float, color: Int, gravity: Int) {
        stateSubTextView?.apply {
            this.gravity = gravity
            text = content
            setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
            setTextColor(color)
        }
    }

    private fun setStateButton(content: String,
                               size: Float,
                               textColor: Int,
                               backgroundColor: Int,
                               listener: ((View) -> Unit)?) {
        stateButton?.apply {
            text = content
            setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
            setTextColor(textColor)
            setBackgroundColor(backgroundColor)
            setOnClickListener(listener)
        }
    }

    fun setErrorButtonClickListener(clickListener: (View) -> Unit) {
        errorButtonListener = clickListener
    }

    fun setEmptyButtonClickListener(clickListener: (View) -> Unit) {
        emptyButtonListener = clickListener
    }

    fun setStateChangeListener(listener: ((newState: STATE, oldState: STATE) -> Unit)) {
        stateChangeListener = listener
    }
}