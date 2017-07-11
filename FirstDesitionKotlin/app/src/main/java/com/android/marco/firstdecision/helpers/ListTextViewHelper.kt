package com.android.marco.firstdecision.helpers

import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.widget.EditText

import com.android.marco.firstdecision.R
import com.android.marco.firstdecision.activities.MainActivity

/**
 * Created by gen on 01.07.15.
 *
 */

class ListTextViewHelper(private val mActivity: MainActivity) {

    fun prepareTextView(e: EditText, checked: Boolean, trashVisibility: Boolean) {
        val r: Drawable
        val d = ResourcesCompat.getDrawable(mActivity.resources,
                android.R.drawable.ic_menu_delete, mActivity.theme)
        val t = Typeface.createFromAsset(e.context.resources.assets,
                "fonts/Tangerine_Bold.ttf")

        if (checked) {
            r = ContextCompat.getDrawable(mActivity,R.drawable.btn_check_buttonless_on)

//                    ResourcesCompat.getDrawable(mActivity.resources,
//                    R.drawable.btn_check_buttonless_on, mActivity.theme)
        }
        else
            r = ContextCompat.getDrawable(mActivity,R.drawable.btn_check_buttonless_off)


        val dimX = (d!!.intrinsicWidth * 1.0).toInt()
        val dimY = (d.intrinsicHeight * 1.0).toInt()
        r.setBounds(0, 0, dimX, dimY + 30)
        d.setBounds(0, 0, dimX, dimY)
        e.setCompoundDrawables(d, null, r, null)
        e.typeface = t

        val mRec = Rect(0, 0, 0, 0)
        if (!trashVisibility) {
            e.compoundDrawables[0].bounds = mRec
        }

    }


}
