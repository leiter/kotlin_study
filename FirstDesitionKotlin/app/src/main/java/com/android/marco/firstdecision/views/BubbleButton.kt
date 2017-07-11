package com.android.marco.firstdecision.views

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.widget.Button
import android.widget.RelativeLayout

import com.android.marco.firstdecision.R

/**
 * Created by gen on 08.11.15.

 */
class BubbleButton : Button {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, title: String, typeface: Typeface, lp: RelativeLayout.LayoutParams) : super(context) {
        this.setTextAppearance(context, R.style.AppBubbles)
        this.isClickable = false
        this.background = ResourcesCompat.getDrawable(resources, R.drawable.bubble, context.theme)
        this.typeface = typeface
        this.layoutParams = lp
        this.text = title
        this.setPadding(48, 48, 48, 48)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    //    public BubbleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    //        super(context, attrs, defStyleAttr, defStyleRes);
    //    }
}
