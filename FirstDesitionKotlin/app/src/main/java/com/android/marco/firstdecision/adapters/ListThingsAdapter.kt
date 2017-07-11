package com.android.marco.firstdecision.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import com.android.marco.firstdecision.R
import com.android.marco.firstdecision.activities.MainActivity
import com.android.marco.firstdecision.dataModels.ThingToDo
import com.android.marco.firstdecision.helpers.ListTextViewHelper
import java.util.*

/**
 * Created by gen on 02.06.15.

 */

class ListThingsAdapter(private val activity: MainActivity, LayoutResourceId: Int, objects: ArrayList<ThingToDo>, trashVisible: Boolean) : ArrayAdapter<ThingToDo>(activity, LayoutResourceId, objects) {
    private val inflater: LayoutInflater
    private var showDelete = false

    init {
        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        showDelete = trashVisible

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position,  parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position,  parent)
    }

    fun getCustomView(position: Int,  parent: ViewGroup): View {
        //TODO use converview
        val tempValues = getItem(position)
        val mOnTouchListener = View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_RIGHT = 2

            if (!(v as EditText).compoundDrawables[DRAWABLE_RIGHT].isVisible)
                return@OnTouchListener false

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= v.getRight() - v.compoundDrawables[DRAWABLE_RIGHT].bounds.width() * 1.5) {
                    val ll = ListTextViewHelper(activity)
                    ll.prepareTextView(v, !tempValues!!.checked, showDelete)
                    getItem(position)!!.checked = !tempValues.checked
                    notifyDataSetChanged()
                    event.action = MotionEvent.ACTION_CANCEL
                    return@OnTouchListener true
                }
                if (event.rawX <= v.compoundDrawables[DRAWABLE_LEFT].bounds.width() * 1.5) {
                    remove(getItem(position))
                    notifyDataSetChanged()
                    event.action = MotionEvent.ACTION_CANCEL
                    return@OnTouchListener true
                }
            }
            false
        }

        val row = inflater.inflate(R.layout.list_edit_text, parent, false) as EditText
        val l = ListTextViewHelper(activity)
        l.prepareTextView(row, tempValues!!.checked, showDelete)
        row.setText(tempValues.named_thing)
        row.setOnTouchListener(mOnTouchListener)
        return row

    }


}
